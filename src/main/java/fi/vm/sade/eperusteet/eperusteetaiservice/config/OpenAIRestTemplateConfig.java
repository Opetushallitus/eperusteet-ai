package fi.vm.sade.eperusteet.eperusteetaiservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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
                .requestFactory(getClientHttpRequestFactory())
                .build();
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(30_000);
        factory.setConnectTimeout(30_000);
        return factory;
    }

}