package fi.vm.sade.eperusteet.eperusteetaiservice.util;

import fi.vm.sade.eperusteet.eperusteetaiservice.dto.FileRequest;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.FileType;
import fi.vm.sade.eperusteet.eperusteetaiservice.dto.LahdeTyyppi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PdfFileUtils {

    private static String environment;

    public static String generateFileName(FileRequest fileRequest) {
        return String.format("%s_%d_%s_%d_%s.%s",
                fileRequest.getLahdeTyyppi().name().toLowerCase(),
                fileRequest.getId(),
                fileRequest.getKieli(),
                fileRequest.getRevision(), environment,
                fileRequest.getFileType().name().toLowerCase());
    }

    @Value("${environment}")
    public void setEnvironment(String name) {
        environment = name;
    }
}
