package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.DataList;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.LahdeTyyppi;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Message;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Run;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.RunMessage;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.RunRequest;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.RunThread;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.List;

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
                .body(new ParameterizedTypeReference<DataList<Message>>() {}).getData();
    }

    public Run runThread(String threadId) {
        RunRequest runRequest = RunRequest.builder()
                .assistantId(assistantService.getAssistantId())
                .temperature(1.0)
                .topP(0.1)
                .model(model)
                .build();

        return openaiRestClient.post()
                .uri(threadUrl + "/" + threadId + "/runs")
                .body(runRequest)
                .retrieve()
                .body(Run.class);
    }

}
