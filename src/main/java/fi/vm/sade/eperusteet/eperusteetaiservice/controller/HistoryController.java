package fi.vm.sade.eperusteet.eperusteetaiservice.controller;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.HistoryDto;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Message;
import fi.vm.sade.eperusteet.eperusteetaiservice.service.HistoryService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@Api("History")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping
    public List<HistoryDto> getHistory() {
        return historyService.getHistory();
    }

    @PostMapping
    public void addHistory(@RequestBody List<Message> messages) {
        historyService.addHistory(messages);
    }
}
