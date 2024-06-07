package fi.vm.sade.eperusteet.eperusteetaiservice.controller;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.LahdeTyyppi;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.OpenAiFile;
import fi.vm.sade.eperusteet.eperusteetaiservice.service.FileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/files")
@Api("Files")
public class FilesController {

    @Autowired
    private FileService fileService;

    @GetMapping
    public List<OpenAiFile> getFiles() {
        return fileService.getFiles();
    }

    @PostMapping("/upload/{lahdeTyyppi}/{id}")
    public OpenAiFile upload(@PathVariable String lahdeTyyppi, @PathVariable Long id, @RequestParam(defaultValue = "fi") String kieli) throws Exception {
        return fileService.upload(LahdeTyyppi.valueOf(lahdeTyyppi.toUpperCase()), id, kieli);
    }

}
