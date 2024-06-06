package fi.vm.sade.eperusteet.eperusteetaiservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class OpenAIRestTemplateConfig {

    @Value("${openai.api.key}")
    private String openaiApiKey;


    @Bean
    public RestClient openaiRestClient() {
        return RestClient.builder()
                .defaultHeader("Authorization", "Bearer " + openaiApiKey)
                .defaultHeader("OpenAI-Beta", "assistants=v2")
                .build();
    }

}