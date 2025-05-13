package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.DokumenttiDto;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.FileRequest;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.FileType;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.LahdeTyyppi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class YlopsExternalService extends ExternalService {

    @Value("${ylops.julkaistu.dokumentti.dto.url}")
    private String ylopsJulkaistuDokumenttiDtoUrl;

    @Value("${ylops.julkaistu.dokumentti.pdf.url}")
    private String ylopsJulkaistuPdfUrl;

    @Value("${ylops.julkaistu.dokumentti.html.url}")
    private String ylopsJulkaistuHtmlUrl;

    @Override
    String getDokumenttiDtoUrl() {
        return ylopsJulkaistuDokumenttiDtoUrl;
    }

    @Override
    String getJulkaistuPdfUrl() {
        return ylopsJulkaistuPdfUrl;
    }

    @Override
    String getJulkaistuHtmlUrl() {
        return ylopsJulkaistuHtmlUrl;
    }

}
