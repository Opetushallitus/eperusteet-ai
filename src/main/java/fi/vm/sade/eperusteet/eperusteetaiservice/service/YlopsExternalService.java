package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class YlopsExternalService extends ExternalService {

    @Value("${ylops.julkaistu.dokumentti.dto.url}")
    private String ylopsJulkaistuDokumenttiDtoUrl;

    @Value("${ylops.julkaistu.dokumentti.pdf.url}")
    private String ylopsJulkaistuPdfUrl;

    @Override
    String getDokumenttiDtoUrl() {
        return ylopsJulkaistuDokumenttiDtoUrl;
    }

    @Override
    String getJulkaistuPdfUrl() {
        return ylopsJulkaistuPdfUrl;
    }

    @Override
    String getPdfName() {
        return "ylops";
    }
}
