package fi.vm.sade.eperusteet.eperusteetaiservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Run {
    private String id;
    @JsonProperty("thread_id")
    private String threadId;
    @JsonProperty("assistant_id")
    private String assistantId;
    private String instructions;
    private String status;
    private String model;
    private Double temperature;
    @JsonProperty("top_p")
    private Double topP;
    private Object last_error;
}
