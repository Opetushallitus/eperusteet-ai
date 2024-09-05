package fi.vm.sade.eperusteet.eperusteetaiservice.controller;

import fi.vm.sade.eperusteet.eperusteetaiservice.service.AppPreferenceService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/available")
@Api("Available")
public class AvailableController {

    @Autowired
    private AppPreferenceService appPreferenceService;

    @GetMapping
    public boolean getIsOpsAiAvailable() {
        return appPreferenceService.isOpsAiAvailable();
    }

}
