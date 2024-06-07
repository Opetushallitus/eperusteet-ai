package fi.vm.sade.eperusteet.eperusteetaiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageContent {
    private String type;
    private MessageText text;
}
