package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.domain.Model;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.DataList;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.ModelDto;
import fi.vm.sade.eperusteet.eperusteetaiservice.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ModelService {

    @Autowired
    private RestClient openaiRestClient;

    @Value("${openai.api.models.url}")
    private String modelsUrl;

    @Autowired
    private ModelRepository modelRepository;

    public List<ModelDto> getModels() {
        return openaiRestClient.get()
                .uri(modelsUrl)
                .retrieve()
                .body(new ParameterizedTypeReference<DataList<ModelDto>>() {})
                .getData()
                .stream().peek(modelDto -> {
                    Optional<Model> model = modelRepository.findById(modelDto.getId());

                    if (model.isPresent()) {
                        modelDto.setDefaultModel(model.get().isDefaultModel());
                        modelDto.setDescription(model.get().getDescription());
                    } else {
                        modelDto.setDefaultModel(false);
                    }
                })
                .sorted(Comparator.comparing(ModelDto::getId))
                .toList();
    }

    public String getDefaultModelId() {
        return modelRepository.findByDefaultModelTrue().getId();
    }
}
