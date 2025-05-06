package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.DokumenttiDto;
import fi.vm.sade.eperusteet.eperusteetaiservice.util.PdfFileNotFoundException;
import fi.vm.sade.eperusteet.eperusteetaiservice.util.PdfFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import java.io.File;
import java.io.IOException;

@Slf4j
public abstract class ExternalService {

    public File getPdf(Long id, String kieli, Integer revision) throws IOException {
        DokumenttiDto dokumenttiDto =
                RestClient.create()
                    .get()
                    .uri(getDokumenttiDtoUrl(), id, kieli, revision)
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (request, response) -> {
                        log.info("PdfFileNotFoundException: Pdf not found for id: {} and kieli: {}", id, kieli);
                        throw new PdfFileNotFoundException();
                    })
                    .body(DokumenttiDto.class);

        if (dokumenttiDto == null) {
            log.info("dokumenttiDto == null, Pdf not found for id: {} and kieli: {}", id, kieli);
            return null;
        }

        byte[] pdfAsArray = RestClient.create()
                .get()
                .uri(getJulkaistuPdfUrl(), dokumenttiDto.getId())
                .retrieve()
                .body(byte[].class);

        if (pdfAsArray == null) {
            log.info("pdfAsArray == null, Pdf not found for id: {} and kieli: {}", id, kieli);
            return null;
        }

        File file = new File(PdfFileUtils.generateFileName(getPdfName(), id,  kieli, revision));
        FileUtils.writeByteArrayToFile(file, pdfAsArray);
        log.info("file fetched from {} with {}", getJulkaistuPdfUrl(), dokumenttiDto.getId());
        return file;
    }

    abstract String getDokumenttiDtoUrl();
    abstract String getJulkaistuPdfUrl();
    abstract String getPdfName();

}
