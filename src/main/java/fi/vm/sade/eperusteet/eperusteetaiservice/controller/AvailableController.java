package fi.vm.sade.eperusteet.eperusteetaiservice.controller;

import fi.vm.sade.eperusteet.eperusteetaiservice.config.AppPreferences;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/available")
@Api("Available")
public class AvailableController {

    @Autowired
    private AppPreferences appPreferences;

    @GetMapping
    public boolean getIsOpsAiAvailable() {
        return appPreferences.getOpsAiAvailable();
    }

    @GetMapping("/set/{opsAiAvailable}")
    public void setIsOpsAiAvailable(@PathVariable boolean opsAiAvailable) {
        appPreferences.setOpsAiAvailable(opsAiAvailable);
    }
}
