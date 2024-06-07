package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AmosaaExternalService extends ExternalService {

    @Value("${amosaa.julkaistu.dokumentti.dto.url}")
    private String amosaaJulkaistuDokumenttiDtoUrl;

    @Value("${amosaa.julkaistu.dokumentti.pdf.url}")
    private String amosaaJulkaistuPdfUrl;

    @Override
    String getDokumenttiDtoUrl() {
        return amosaaJulkaistuDokumenttiDtoUrl;
    }

    @Override
    String getJulkaistuPdfUrl() {
        return amosaaJulkaistuPdfUrl;
    }

    @Override
    String getPdfName() {
        return "amosaa";
    }
}
