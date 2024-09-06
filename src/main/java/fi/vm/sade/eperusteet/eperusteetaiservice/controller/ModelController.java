package fi.vm.sade.eperusteet.eperusteetaiservice.controller;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Model;
import fi.vm.sade.eperusteet.eperusteetaiservice.service.ModelService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/model")
@Api("Model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @GetMapping
    public List<Model> getModels() {
        return modelService.getModels();
    }
}
