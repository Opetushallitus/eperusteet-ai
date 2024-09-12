package fi.vm.sade.eperusteet.eperusteetaiservice.domain;

import lombok.Data;

import java.util.Map;

@Data
public class MessageMeta {
    private String educationLevel;
    private Map<String, String> sourceName;
    private String sourceType;
    private Integer sourceId;
    private String sourceLanguage;
    private Integer sourceRevision;
}
