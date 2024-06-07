package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.Usage;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.UsageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class UsageService {

    @Autowired
    private RestClient openaiRestClient;

    @Value("${openai.api.usage.url}")
    private String usageUrl;

    public Double getUsage() {
        LocalDate startDate = LocalDate.of(2024, 6, 1);
        Integer sumTokens = 0;

        while(startDate.isBefore(LocalDate.now())) {
            sumTokens += getSumUsage(startDate);
            startDate = startDate.plusDays(1);
        }

        return sumTokens.doubleValue() / 1000.0;
    }

    public Integer getSumUsage(LocalDate date) {
        return openaiRestClient.get()
                .uri(usageUrl, date.format(DateTimeFormatter.ISO_DATE))
                .retrieve()
                .body(Usage.class)
                .getData()
                .stream().map(UsageData::getContextTokensTotal)
                .reduce(0, Integer::sum);
    }
}
