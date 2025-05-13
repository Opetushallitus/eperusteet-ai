package fi.vm.sade.eperusteet.eperusteetaiservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.FileType;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageMeta {
    private String educationLevel;
    private Map<String, String> sourceName;
    private String sourceType;
    private Integer sourceId;
    private String sourceLanguage;
    private Integer sourceRevision;
    private FileType sourceFileType;
    private String instructions;
    private Double temperature;
    @JsonProperty("top_p")
    private Double topP;
    private String model;
}
