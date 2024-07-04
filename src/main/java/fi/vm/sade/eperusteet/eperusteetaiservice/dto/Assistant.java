package fi.vm.sade.eperusteet.eperusteetaiservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assistant {
    private String id;
    private String object;
    private String name;
    private String instructions;
    private Double temperature;
    private String model;
    @JsonProperty("top_p")
    private Double topP;
}
