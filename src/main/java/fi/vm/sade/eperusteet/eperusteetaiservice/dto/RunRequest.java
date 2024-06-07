package fi.vm.sade.eperusteet.eperusteetaiservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RunRequest {
    @JsonProperty("assistant_id")
    private String assistantId;
    private String instructions;
    private String model;
    private Double temperature;
    @JsonProperty("top_p")
    private Double topP;
}
