package fi.vm.sade.eperusteet.eperusteetaiservice.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PdfFileUtils {

    public static String generateFileName(String tyyppi, Long id, String kieli, Integer revision) {
        return String.format("%s_%d_%s_%d.pdf", tyyppi, id, kieli, revision);
    }
}
