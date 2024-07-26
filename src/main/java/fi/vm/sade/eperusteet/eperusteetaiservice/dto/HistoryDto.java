package fi.vm.sade.eperusteet.eperusteetaiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDto {
    private String messageId;
    private String threadId;
    private Date createdAt;
    private String role;
    private String sourceType;
    private String sourceId;
    private String sourceLanguage;
    private String sourceRevision;
    private String content;
}
