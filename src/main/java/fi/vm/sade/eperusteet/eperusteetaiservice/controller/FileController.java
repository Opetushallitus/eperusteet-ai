package fi.vm.sade.eperusteet.eperusteetaiservice.controller;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.FileRequest;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.FileType;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.LahdeTyyppi;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.OpenAiFile;
import fi.vm.sade.eperusteet.eperusteetaiservice.service.FileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/file")
@Api("File")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping
    public List<OpenAiFile> getFiles() {
        return fileService.getFiles();
    }

    @PostMapping("/upload/{lahdeTyyppi}/{id}/{kieli}/{revision}/{type}")
    public OpenAiFile upload(
            @PathVariable String lahdeTyyppi,
            @PathVariable Long id,
            @PathVariable String kieli,
            @PathVariable Integer revision,
            @PathVariable String type) throws Exception {
        return fileService.upload(
                FileRequest.builder()
                        .lahdeTyyppi(LahdeTyyppi.valueOf(lahdeTyyppi.toUpperCase()))
                        .id(id)
                        .kieli(kieli)
                        .revision(revision)
                        .fileType(FileType.valueOf(type.toUpperCase()))
                        .build());
    }

    @GetMapping("/supportedtypes/{lahdeTyyppi}/{id}/{kieli}/{revision}")
    public List<String> getSupportedTypes(
            @PathVariable String lahdeTyyppi,
            @PathVariable Long id,
            @PathVariable String kieli,
            @PathVariable Integer revision) {
        return fileService.getSupportedTypes(
                FileRequest.builder()
                        .lahdeTyyppi(LahdeTyyppi.valueOf(lahdeTyyppi.toUpperCase()))
                        .id(id)
                        .kieli(kieli)
                        .revision(revision)
                        .build());
    }

    @GetMapping("/sourceurl/{lahdeTyyppi}/{id}/{kieli}/{revision}/{type}")
    public String getSourceUrl(
            @PathVariable String lahdeTyyppi,
            @PathVariable Long id,
            @PathVariable String kieli,
            @PathVariable Integer revision,
            @PathVariable String type) {
        return fileService.getSourceUrl(
                FileRequest.builder()
                        .lahdeTyyppi(LahdeTyyppi.valueOf(lahdeTyyppi.toUpperCase()))
                        .id(id)
                        .kieli(kieli)
                        .revision(revision)
                        .fileType(FileType.valueOf(type.toUpperCase()))
                        .build());
    }

}
