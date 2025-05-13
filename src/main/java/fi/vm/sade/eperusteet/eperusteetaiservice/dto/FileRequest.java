package fi.vm.sade.eperusteet.eperusteetaiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileRequest {
    private LahdeTyyppi lahdeTyyppi;
    private Long id;
    private String kieli;
    private Integer revision;
    private FileType fileType;
}
