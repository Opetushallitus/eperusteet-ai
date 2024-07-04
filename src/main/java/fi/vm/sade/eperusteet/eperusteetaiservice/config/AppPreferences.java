package fi.vm.sade.eperusteet.eperusteetaiservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

@ApplicationScope
@Component
@Getter
@Setter
public class AppPreferences {
    private Boolean opsAiAvailable = true;
}
