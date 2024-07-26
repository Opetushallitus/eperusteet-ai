package fi.vm.sade.eperusteet.eperusteetaiservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultConfigs {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
