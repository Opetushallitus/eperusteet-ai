package fi.vm.sade.eperusteet.eperusteetaiservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenAiFile {

    private String id;
    private Integer bytes;
    @JsonProperty("created_at")
    private Integer createdAt;
    private String filename;
    private String object;
    private String purpose;
}
