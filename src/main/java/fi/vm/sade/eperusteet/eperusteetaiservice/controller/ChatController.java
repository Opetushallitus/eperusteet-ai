package fi.vm.sade.eperusteet.eperusteetaiservice.controller;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.LahdeTyyppi;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Message;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Run;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Thread;
import fi.vm.sade.eperusteet.eperusteetaiservice.service.AssistantService;
import fi.vm.sade.eperusteet.eperusteetaiservice.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController()
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private AssistantService assistantService;

    @Autowired
    private ThreadService threadService;

    @GetMapping("/create")
    public Thread createThread() {
        return threadService.createThread();
    }

    @GetMapping("/messages/{threadId}")
    public List<Message> getMessages(@PathVariable String threadId) {
        return threadService.getMessages(threadId);
    }

    @GetMapping("/add/{threadId}/{lahdeTyyppi}/{id}")
    public Message addMessage(
            @PathVariable String threadId,
            @PathVariable String lahdeTyyppi,
            @PathVariable Long id,
            @RequestParam String prompt,
            @RequestParam(defaultValue = "fi") String kieli
    ) throws Exception {
        return threadService.addMessageToThread(threadId, LahdeTyyppi.valueOf(lahdeTyyppi.toUpperCase()), id, prompt, kieli);
    }

    @GetMapping("/run/{threadId}")
    public Run runThread(@PathVariable String threadId) {
        return threadService.runThread(threadId);
    }

}