package fi.vm.sade.eperusteet.eperusteetaiservice.dto;

import fi.vm.sade.eperusteet.eperusteetaiservice.domain.MessageMeta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private String messageId;
    private String threadId;
    private Date createdAt;
    private String role;
    private String content;
    private MessageMeta meta;
    private FeedbackDto feedback;
}
