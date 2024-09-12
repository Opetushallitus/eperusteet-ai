package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.domain.Feedback;
import fi.vm.sade.eperusteet.eperusteetaiservice.domain.Message;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.FeedbackDto;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.LahdeTyyppi;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.MessageDto;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.ThreadMessagesDto;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.ThreadWithCreateTime;
import fi.vm.sade.eperusteet.eperusteetaiservice.repository.FeedbackRepository;
import fi.vm.sade.eperusteet.eperusteetaiservice.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class MessageService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private MessageRepository messageRepository;

    public void addHistory(List<MessageDto> messages) {
        messages.forEach(messageDto -> {
            log.trace("Adding history for message: {}", messageDto);

            if (messageRepository.findByMessageId(messageDto.getMessageId()).isPresent() || messageDto.getThreadId() == null) {
                return;
            }

            Message message = modelMapper.map(messageDto, Message.class);
            messageRepository.save(message);
        });
    }

    public Page<ThreadMessagesDto> getMessages(Date startDate, Date endDate, List<String> sourceType, String educationLevel, Boolean hasFeedbackOnly, Integer page, Integer pagesize) {
        Pageable pageable = PageRequest.of(page, pagesize);
        Page<String> threadIds = messageRepository.findByParameters(
                    Optional.ofNullable(startDate).orElse(new Date(1l)),
                    Optional.ofNullable(endDate).orElse(new Date()),
                    Optional.ofNullable(sourceType)
                            .orElse(List.of(
                                    LahdeTyyppi.PERUSTE.name().toLowerCase(),
                                    LahdeTyyppi.AMOSAA.name().toLowerCase(),
                                    LahdeTyyppi.YLOPS.name().toLowerCase())),
                    Optional.ofNullable(educationLevel).orElse(""),
                    hasFeedbackOnly,
                    pageable
                ).map(ThreadWithCreateTime::getThread_id);

        Map<String, List<MessageDto>> messageMap = messageRepository.findByThreadIdIn(threadIds.getContent()).stream()
                .map(message -> modelMapper.map(message, MessageDto.class))
                .collect(Collectors.groupingBy(MessageDto::getThreadId));

        List<ThreadMessagesDto> threadMessagesDtos = messageMap.keySet().stream()
                .map(threadId -> new ThreadMessagesDto(threadId, messageMap.get(threadId)))
                .toList();

        return new PageImpl<>(threadMessagesDtos, pageable, threadIds.getTotalElements());
    }

    public List<MessageDto> getMessagesByThreadId(String threadId) {
        return messageRepository.findByThreadId(threadId).stream()
                .map(message -> modelMapper.map(message, MessageDto.class))
                .toList();
    }

    public FeedbackDto addFeedback(String messageId, FeedbackDto feedbackDto) {
        log.trace("Adding feedback: {}", feedbackDto);

        Message message = messageRepository.findByMessageId(messageId).orElseThrow();

        Feedback feedback;
        if (message.getFeedback() != null) {
            feedback = message.getFeedback();
            feedback.setResult(feedbackDto.getResult());
            feedback.setComment(feedbackDto.getComment());
        } else {
            feedback = modelMapper.map(feedbackDto, Feedback.class);
            feedback.setMessage(message);
        }

        feedback = feedbackRepository.save(feedback);
        return modelMapper.map(feedback, FeedbackDto.class);
    }
}
