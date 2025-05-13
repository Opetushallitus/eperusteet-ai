package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.DokumenttiDto;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.FileRequest;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.FileType;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.LahdeTyyppi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class PerusteetExternalService extends ExternalService {

    @Value("${eperusteet.julkaistu.dokumentti.dto.url}")
    private String eperustusteetJulkaistuDokumenttiDtoUrl;

    @Value("${eperusteet.julkaistu.dokumentti.pdf.url}")
    private String eperustusteetJulkaistuPdfUrl;

    @Value("${eperusteet.julkaistu.dokumentti.html.url}")
    private String eperustusteetJulkaistuHtmlUrl;

    @Override
    String getDokumenttiDtoUrl() {
        return eperustusteetJulkaistuDokumenttiDtoUrl;
    }

    @Override
    String getJulkaistuPdfUrl() {
        return eperustusteetJulkaistuPdfUrl;
    }

    @Override
    String getJulkaistuHtmlUrl() { return eperustusteetJulkaistuHtmlUrl; }
}
