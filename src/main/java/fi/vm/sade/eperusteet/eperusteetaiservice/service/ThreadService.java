package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.DataList;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.LahdeTyyppi;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Message;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Run;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.RunMessage;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ThreadService {

    @Autowired
    private RestClient openaiRestClient;

    @Autowired
    private AssistantService assistantService;

    @Autowired
    private FileService fileService;

    @Value("${openai.api.threads.url}")
    private String threadUrl;

    @Value("${openai.api.messages.url}")
    private String messagesUrl;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.default.temperature}")
    private Double defaultTemperature;

    @Value("${openai.api.default.top_p}")
    private Double defaultTopP;

    public Thread createThread() {
        return openaiRestClient.post()
                .uri(threadUrl)
                .retrieve()
                .body(Thread.class);
    }

    public Message addMessageToThread(String threadId, LahdeTyyppi lahdeTyyppi, Long id, String prompt, String kieli) throws IOException {
        String fileId = fileService.upload(lahdeTyyppi, id, kieli).getId();
        RunMessage message = new RunMessage(prompt, fileId);

        return openaiRestClient.post()
                .uri(threadUrl + "/" + threadId + "/messages")
                .body(message)
                .retrieve()
                .body(Message.class);
    }

    public List<Message> getMessages(String threadId) {
        return openaiRestClient.get()
                .uri(threadUrl + "/" + threadId + "/messages")
                .retrieve()
                .body(new ParameterizedTypeReference<DataList<Message>>() {}).getData()
                .stream().sorted(Comparator.comparing(Message::getCreatedAt)).toList();
    }

    public Run runThread(String threadId, String instructions, Double temperature, Double topP) {
        Run runRequest = Run.builder()
                .assistantId(assistantService.getAssistantId())
                .instructions(instructions)
                .temperature(Optional.ofNullable(temperature).orElse(defaultTemperature))
                .topP(Optional.ofNullable(topP).orElse(defaultTopP))
                .model(model)
                .build();

        return openaiRestClient.post()
                .uri(threadUrl + "/" + threadId + "/runs")
                .body(runRequest)
                .retrieve()
                .body(Run.class);
    }

    public Run getRunStatus(String threadId, String runId) {
        return openaiRestClient.get()
                .uri(threadUrl + "/" + threadId + "/runs/" + runId)
                .retrieve()
                .body(Run.class);
    }

}
