package fi.vm.sade.eperusteet.eperusteetaiservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PdfFileUtils {

    private static String environment;

    public static String generateFileName(String tyyppi, Long id, String kieli, Integer revision) {
        return String.format("%s_%d_%s_%d_%s.pdf", tyyppi, id, kieli, revision, environment);
    }

    @Value("${environment}")
    public void setEnvironment(String name) {
        environment = name;
    }
}
