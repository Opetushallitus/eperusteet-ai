package fi.vm.sade.eperusteet.eperusteetaiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DokumenttiDto {
    private Long id;
    private int julkaisuRevision;
    private List<String> dataTyypit;
}
