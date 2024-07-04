package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.DataList;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Service
public class ModelService {

    @Autowired
    private RestClient openaiRestClient;

    @Value("${openai.api.models.url}")
    private String modelsUrl;

    @Value("${openai.model}")
    private String defaultModel;

    public List<String> getModels() {
        return Arrays.asList(defaultModel, "gpt-4o-mini");
//        return openaiRestClient.get()
//                .uri(modelsUrl)
//                .retrieve()
//                .body(new ParameterizedTypeReference<DataList<Model>>() {})
//                .getData()
//                .stream().peek(model -> {
//                    if (model.getId().equals(defaultModel)) {
//                        model.setDefaultModel(true);
//                    }
//                }).toList();
    }
}
