package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.DataList;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.MessageDto;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.OpenaiMessage;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Run;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.RunMessage;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Thread;
import fi.vm.sade.eperusteet.eperusteetaiservice.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ThreadService {

    @Autowired
    private RestClient openaiRestClient;

    @Autowired
    private AssistantService assistantService;

    @Autowired
    private FileService fileService;

    @Autowired
    private AppPreferenceService appPreferenceService;

    @Value("${openai.api.threads.url}")
    private String threadUrl;

    @Value("${openai.api.messages.url}")
    private String messagesUrl;

    @Value("${openai.model}")
    private String defaultModel;

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

    public OpenaiMessage addMessageToThread(String threadId, String fileId, String prompt) throws IOException {
        if (!appPreferenceService.isOpsAiAvailable()) {
            throw new RuntimeException("OpsAI not available");
        }

        RunMessage message = new RunMessage(prompt, fileId);
        return openaiRestClient.post()
                .uri(threadUrl + "/" + threadId + "/messages")
                .body(message)
                .retrieve()
                .body(OpenaiMessage.class);
    }

    public List<OpenaiMessage> getMessages(String threadId) {
        String messages = openaiRestClient.get()
                .uri(threadUrl + "/" + threadId + "/messages")
                .retrieve()
                .body(String.class);
        log.debug("messages: {}", messages);
        return openaiRestClient.get()
                .uri(threadUrl + "/" + threadId + "/messages")
                .retrieve()
                .body(new ParameterizedTypeReference<DataList<OpenaiMessage>>() {}).getData()
                .stream()
                .peek(message -> message.setContent(message.getContent().stream().peek(content -> content.getText().setValue(content.getText().getValue()
                        .replaceAll("(?:【[^】]*】)*", "")
                        .replaceAll("\\*\\*", "")))
                        .toList()))
                .sorted(Comparator.comparing(OpenaiMessage::getCreatedAt)).toList();
    }

    public Run runThread(String threadId, String model, String instructions, Double temperature, Double topP) {
        if (!appPreferenceService.isOpsAiAvailable()) {
            throw new RuntimeException("OpsAI not available");
        }

        Run runRequest = Run.builder()
                .assistantId(assistantService.getAssistantId())
                .instructions(instructions)
                .temperature(Optional.ofNullable(temperature).orElse(defaultTemperature))
                .topP(Optional.ofNullable(topP).orElse(defaultTopP))
                .model(Optional.ofNullable(model).orElse(defaultModel))
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
