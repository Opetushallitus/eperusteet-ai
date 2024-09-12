package fi.vm.sade.eperusteet.eperusteetaiservice.controller;

import fi.vm.sade.eperusteet.eperusteetaiservice.domain.Feedback;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.FeedbackDto;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.MessageDto;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.ThreadMessagesDto;
import fi.vm.sade.eperusteet.eperusteetaiservice.service.MessageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@Api("Message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public Page<ThreadMessagesDto> getMessages(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date endDate,
            @RequestParam(required = false) List<String> sourceType,
            @RequestParam(required = false) String educationLevel,
            @RequestParam(required = false, defaultValue="false") Boolean hasFeedbackOnly,
            @RequestParam(value = "page", defaultValue = "0", required = false) final Integer page,
            @RequestParam(value = "pagesize", defaultValue = "10", required = false) final Integer pagesize
            ) {
        return messageService.getMessages(startDate, endDate, sourceType, educationLevel, hasFeedbackOnly, page, pagesize);
    }

    @PostMapping
    public void addMessage(@RequestBody List<MessageDto> messages) {
        messageService.addHistory(messages);
    }

    @PostMapping("/feedback/{messageId}")
    public FeedbackDto addFeedback(@PathVariable String messageId, @RequestBody FeedbackDto feedbackDto) {
        return messageService.addFeedback(messageId, feedbackDto);
    }

    @GetMapping("/thread/{threadId}")
    public List<MessageDto> getMessagesByThreadId(@PathVariable String threadId) {
        return messageService.getMessagesByThreadId(threadId);
    }

}
