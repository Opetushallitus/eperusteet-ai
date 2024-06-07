package fi.vm.sade.eperusteet.eperusteetaiservice.service;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.DataList;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.LahdeTyyppi;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.OpenAiFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
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

    private Optional<OpenAiFile> findFile(LahdeTyyppi lahdeTyyppi, Long id) {
        return getFiles().stream()
                .filter(file -> file.getFilename().equals(lahdeTyyppi.toString().toLowerCase() + "_" + id))
                .findFirst();
    }

    public OpenAiFile upload(LahdeTyyppi lahdeTyyppi, Long id, String kieli) throws IOException {
        Optional<OpenAiFile> fileExists = findFile(lahdeTyyppi, id);
        if (fileExists.isPresent()) {
            return fileExists.get();
        }

        File file = getExternalDataService(lahdeTyyppi).getPdf(id, kieli);

        if (file == null) {
            throw new FileNotFoundException("File not found");
        }

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
