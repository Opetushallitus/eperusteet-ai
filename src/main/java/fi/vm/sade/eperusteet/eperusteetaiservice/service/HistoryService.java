package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.domain.History;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Attachment;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.HistoryDto;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Message;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.MessageContent;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.MessageText;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.OpenAiFile;
import fi.vm.sade.eperusteet.eperusteetaiservice.repository.HistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class HistoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private HistoryRepository historyRepository;

    public void addHistory(List<Message> messages) {
        String fileId = messages.stream()
                .map(Message::getAttachments)
                .flatMap(Collection::stream)
                .map(Attachment::getFileId)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        Optional<OpenAiFile> file = fileService.getFile(fileId);
        messages.forEach(message -> {
            log.trace("Adding history for message: {}", message);

            if (message.getId() == null || historyRepository.findByMessageId(message.getId()).isPresent()) {
                return;
            }


            History history = new History();
            history.setMessageId(message.getId());
            history.setThreadId(message.getThreadId());
            history.setRole(message.getRole());
            history.setCreatedAt(new Date(Long.parseLong(message.getCreatedAt()) * 1000));
            if (file.isPresent()) {
                String[] parts = file.get().getFilename().replace(".pdf", "").split("_");
                history.setSourceType(parts[0]);
                history.setSourceId(parts[1]);
                history.setSourceLanguage(parts[2]);
                history.setSourceRevision(parts[3]);
            }
            history.setContent(message.getContent().stream().findFirst().map(MessageContent::getText).map(MessageText::getValue).orElse(null));
            historyRepository.save(history);
        });
    }

    public List<HistoryDto> getHistory() {
        return historyRepository.findAll().stream()
                .map(history -> modelMapper.map(history, HistoryDto.class))
                .toList();
    }
}
