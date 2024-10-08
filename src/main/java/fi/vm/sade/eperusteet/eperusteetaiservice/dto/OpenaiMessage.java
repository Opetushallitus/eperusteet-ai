package fi.vm.sade.eperusteet.eperusteetaiservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenaiMessage {
    private String id;
    @JsonProperty("thread_id")
    private String threadId;
    @JsonProperty("created_at")
    private String createdAt;
    private String status;
    private String role;
    private List<MessageContent> content = new ArrayList<>();
    private List<Attachment> attachments = new ArrayList<>();
}
