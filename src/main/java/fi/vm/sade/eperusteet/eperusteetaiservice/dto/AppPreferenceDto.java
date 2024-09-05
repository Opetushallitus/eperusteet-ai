package fi.vm.sade.eperusteet.eperusteetaiservice.dto;

import fi.vm.sade.eperusteet.eperusteetaiservice.domain.AppPreferenceProperty;
import lombok.Data;

@Data
public class AppPreferenceDto {
    private AppPreferenceProperty property;
    private String value;
}
