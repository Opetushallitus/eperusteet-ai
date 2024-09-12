package fi.vm.sade.eperusteet.eperusteetaiservice.dto;

import fi.vm.sade.eperusteet.eperusteetaiservice.domain.FeedbackResult;
import lombok.Data;

@Data
public class FeedbackDto {
    private FeedbackResult result;
    private String comment;
}
