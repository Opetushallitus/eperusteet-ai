package fi.vm.sade.eperusteet.eperusteetaiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelDto {
    private String id;
    private boolean defaultModel;
    private String description;
}
