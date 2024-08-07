package fi.vm.sade.eperusteet.eperusteetaiservice.controller;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Assistant;
import fi.vm.sade.eperusteet.eperusteetaiservice.service.AssistantService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/assistant")
@Api("Assistant")
public class AssistantController {

    @Autowired
    private AssistantService assistantService;

    @GetMapping
    public List<Assistant> getAssistants() {
        return assistantService.getAssistants();
    }

}
