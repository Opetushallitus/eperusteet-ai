package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.FileRequest;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.FileType;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.LahdeTyyppi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AmosaaExternalService extends ExternalService {

    @Value("${amosaa.julkaistu.dokumentti.dto.url}")
    private String amosaaJulkaistuDokumenttiDtoUrl;

    @Value("${amosaa.julkaistu.dokumentti.pdf.url}")
    private String amosaaJulkaistuPdfUrl;

    @Value("${amosaa.julkaistu.dokumentti.html.url}")
    private String amosaaJulkaistuHtmlUrl;

    @Override
    String getDokumenttiDtoUrl() {
        return amosaaJulkaistuDokumenttiDtoUrl;
    }

    @Override
    String getJulkaistuPdfUrl() {
        return amosaaJulkaistuPdfUrl;
    }

    @Override
    String getJulkaistuHtmlUrl() {
        return amosaaJulkaistuHtmlUrl;
    }
}
