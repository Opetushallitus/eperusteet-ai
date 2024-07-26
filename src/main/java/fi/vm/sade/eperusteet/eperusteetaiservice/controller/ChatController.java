package fi.vm.sade.eperusteet.eperusteetaiservice.controller;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.LahdeTyyppi;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Message;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Run;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Thread;
import fi.vm.sade.eperusteet.eperusteetaiservice.service.AssistantService;
import fi.vm.sade.eperusteet.eperusteetaiservice.service.ThreadService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController()
@RequestMapping("/api/chat")
@Api("Chat")
public class ChatController {

    @Autowired
    private AssistantService assistantService;

    @Autowired
    private ThreadService threadService;

    @PostMapping("/create")
    public Thread createThread() {
        return threadService.createThread();
    }

    @GetMapping("/messages/{threadId}")
    public List<Message> getMessages(@PathVariable String threadId) {
        return threadService.getMessages(threadId);
    }

    @PostMapping("/add/{threadId}/{fileId}")
    public Message addMessage(
            @PathVariable String threadId,
            @PathVariable String fileId,
            @RequestParam String prompt
    ) throws Exception {
        return threadService.addMessageToThread(threadId, fileId, prompt);
    }

    @PostMapping("/run/{threadId}")
    public Run runThread(
            @PathVariable String threadId,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String instructions,
            @RequestParam(required = false) Double temperature,
            @RequestParam(required = false) Double topP) {
        return threadService.runThread(threadId, model, instructions, temperature, topP);
    }

    @GetMapping("/thread/{threadId}/run/{runId}")
    public Run getRun(@PathVariable String threadId, @PathVariable String runId) {
        return threadService.getRunStatus(threadId, runId);
    }

}