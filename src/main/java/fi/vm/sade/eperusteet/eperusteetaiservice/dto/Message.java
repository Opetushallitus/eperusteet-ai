package fi.vm.sade.eperusteet.eperusteetaiservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String id;
    @JsonProperty("thread_id")
    private String threadId;
    @JsonProperty("created_at")
    private String createdAt;
    private String status;
    private String role;
    private List<MessageContent> content;
    private List<Attachment> attachments;

    public Message(String promt, String fileId) {
        this.role = "user";
        this.content = List.of(new MessageContent("text", new MessageText(promt)));
        this.attachments = List.of(new Attachment(fileId, List.of(new Tool("file_search"))));
    }
}
