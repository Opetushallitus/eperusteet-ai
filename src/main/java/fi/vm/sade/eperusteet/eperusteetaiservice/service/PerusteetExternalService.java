package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PerusteetExternalService extends ExternalService {

    @Value("${eperusteet.julkaistu.dokumentti.dto.url}")
    private String eperustusteetJulkaistuDokumenttiDtoUrl;

    @Value("${eperusteet.julkaistu.dokumentti.pdf.url}")
    private String eperustusteetJulkaistuPdfUrl;

    @Override
    String getDokumenttiDtoUrl() {
        return eperustusteetJulkaistuDokumenttiDtoUrl;
    }

    @Override
    String getJulkaistuPdfUrl() {
        return eperustusteetJulkaistuPdfUrl;
    }

    @Override
    String getPdfName() {
        return "peruste";
    }
}
