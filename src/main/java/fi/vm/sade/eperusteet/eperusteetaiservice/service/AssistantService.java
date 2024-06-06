package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Assistant;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.DataList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AssistantService  {

    @Autowired
    private RestClient openaiRestClient;

    @Value("${openai.api.assistants.url}")
    private String assistantsUrl;

    public List<Assistant> getAssistants() {
        return openaiRestClient.get()
                .uri(assistantsUrl)
                .retrieve()
                .body(new ParameterizedTypeReference<DataList<Assistant>>() {}).getData();
    }

    public String getAssistantId() {
        return getAssistants().get(0).getId();
    }
}
