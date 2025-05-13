package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.DokumenttiDto;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.FileRequest;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.FileType;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.LahdeTyyppi;
import fi.vm.sade.eperusteet.eperusteetaiservice.util.PdfFileNotFoundException;
import fi.vm.sade.eperusteet.eperusteetaiservice.util.PdfFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;

@Slf4j
public abstract class ExternalService {

    public File getFile(FileRequest fileRequest) throws IOException {
        DokumenttiDto dokumenttiDto = getDokumenttiDto(fileRequest);

        if (dokumenttiDto == null) {
            log.info("dokumenttiDto == null, Pdf not found for : {} ", fileRequest);
            return null;
        }

        byte[] pdfAsArray = RestClient.create()
                .get()
                .uri(getUrl(fileRequest.getFileType()), dokumenttiDto.getId())
                .retrieve()
                .body(byte[].class);

        if (pdfAsArray == null) {
            log.info("pdfAsArray == null, Pdf not found for: {} ", fileRequest);
            return null;
        }

        File file = new File(PdfFileUtils.generateFileName(fileRequest));
        FileUtils.writeByteArrayToFile(file, pdfAsArray);
        log.info("file fetched from {} with {}", getJulkaistuPdfUrl(), dokumenttiDto.getId());
        return file;
    }

    public DokumenttiDto getDokumenttiDto(FileRequest fileRequest) {
        return RestClient.create()
                .get()
                .uri(getDokumenttiDtoUrl(), fileRequest.getId(), fileRequest.getKieli(), fileRequest.getRevision())
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    log.info("PdfFileNotFoundException: Pdf not found for {}", fileRequest);
                    throw new PdfFileNotFoundException();
                })
                .body(DokumenttiDto.class);
    }

    private String getUrl(FileType fileType) {
        if (fileType == FileType.PDF) {
            return getJulkaistuPdfUrl();
        }

        return getJulkaistuHtmlUrl();
    }

    public String getSourceUrl(FileRequest fileRequest) {
        DokumenttiDto dokumenttiDto = getDokumenttiDto(fileRequest);
        return UriComponentsBuilder
                .fromUriString(fileRequest.getFileType().equals(FileType.PDF) ? getJulkaistuPdfUrl() : getJulkaistuHtmlUrl())
                .buildAndExpand(dokumenttiDto.getId())
                .toUriString();
    }

    abstract String getDokumenttiDtoUrl();
    abstract String getJulkaistuPdfUrl();
    abstract String getJulkaistuHtmlUrl();
}
