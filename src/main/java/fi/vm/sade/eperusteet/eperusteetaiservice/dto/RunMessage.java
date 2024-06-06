package fi.vm.sade.eperusteet.eperusteetaiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RunMessage {
    private String role;
    private String content;
    private List<Attachment> attachments;

    public RunMessage(String promt, String fileId) {
        this.role = "user";
        this.content = promt;
        this.attachments = List.of(new Attachment(fileId, List.of(new Tool("file_search"))));
    }
}
