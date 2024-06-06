package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.DokumenttiDto;
import org.apache.commons.io.FileUtils;
import org.springframework.web.client.RestClient;

import java.io.File;
import java.io.IOException;

public abstract class ExternalService {

    public File getPdf(Long id, String kieli) throws IOException {
        DokumenttiDto dokumenttiDto =
                RestClient.create()
                    .get()
                    .uri(getDokumenttiDtoUrl(), id, kieli)
                    .retrieve().body(DokumenttiDto.class);

        if (dokumenttiDto == null) {
            return null;
        }

        byte[] pdfAsArray = RestClient.create()
                .get()
                .uri(getJulkaistuPdfUrl(), dokumenttiDto.getId())
                .retrieve()
                .body(byte[].class);

        if (pdfAsArray == null) {
            return null;
        }

        File file = new File(getPdfName() + "_" + id + ".pdf");
        FileUtils.writeByteArrayToFile(file, pdfAsArray);
        return file;
    }

    abstract String getDokumenttiDtoUrl();
    abstract String getJulkaistuPdfUrl();
    abstract String getPdfName();
}
