package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.DataList;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.LahdeTyyppi;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.OpenAiFile;
import fi.vm.sade.eperusteet.eperusteetaiservice.util.PdfFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FileService {

    @Autowired
    private RestClient openaiRestClient;

    @Value("${openai.api.files.url}")
    private String filesUrl;

    @Autowired
    private PerusteetExternalService perusteetExternalService;

    @Autowired
    private YlopsExternalService ylopsExternalService;

    @Autowired
    private AmosaaExternalService amosaaExternalService;

    public List<OpenAiFile> getFiles() {
        return openaiRestClient.get()
                .uri(filesUrl)
                .retrieve()
                .body(new ParameterizedTypeReference<DataList<OpenAiFile>>() {}).getData();
    }

    private Optional<OpenAiFile> findFile(LahdeTyyppi lahdeTyyppi, Long id, String kieli, Integer revision) {
        return getFiles().stream()
                .filter(file -> file.getFilename().equals(PdfFileUtils.generateFileName(lahdeTyyppi.toString().toLowerCase(), id,  kieli, revision)))
                .findFirst();
    }

    public OpenAiFile upload(LahdeTyyppi lahdeTyyppi, Long id, String kieli, Integer revision) throws IOException {
        log.info("uploading file: {}, {}, {}, {}", lahdeTyyppi, id, kieli, revision);
        Optional<OpenAiFile> fileExists = findFile(lahdeTyyppi, id, kieli, revision);
        log.info("fileExists: {}", fileExists.isPresent());
        if (fileExists.isPresent()) {
            return fileExists.get();
        }

        File file = getExternalDataService(lahdeTyyppi).getPdf(id, kieli, revision);
        return upload(file);
    }

    private ExternalService getExternalDataService(LahdeTyyppi lahdeTyyppi) {
        switch (lahdeTyyppi) {
            case PERUSTE:
                return perusteetExternalService;
            case YLOPS:
                return ylopsExternalService;
            case AMOSAA:
                return amosaaExternalService;
            default:
                throw new RuntimeException("wrong lahdetyyppi");
        }
    }

    public OpenAiFile upload(File file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new FileSystemResource(file)).header("Content-Disposition", "form-data; name=file; filename=" + file.getName());
        builder.part("purpose", "assistants");

        OpenAiFile openAiFile =  openaiRestClient.post()
                .uri(filesUrl)
                .body(builder.build())
                .retrieve()
                .body(OpenAiFile.class);
        file.delete();
        return openAiFile;
    }
}
