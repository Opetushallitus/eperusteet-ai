package fi.vm.sade.eperusteet.eperusteetaiservice.controller;

import fi.vm.sade.eperusteet.eperusteetaiservice.service.UsageService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usage")
@Api("Usage")
public class UsageController {

    @Autowired
    private UsageService usageService;

    @GetMapping
    public Double getUsage() {
        return usageService.getUsage();
    }
}
