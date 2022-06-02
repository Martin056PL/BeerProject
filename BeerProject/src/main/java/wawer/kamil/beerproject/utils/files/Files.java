package wawer.kamil.beerproject.utils.files;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import wawer.kamil.beerproject.exceptions.FileProcessingException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class Files {

    public static String getEmailContent(String pathFile) {
        try {
            FileInputStream fileInputStream = new FileInputStream(pathFile);
            return IOUtils.toString(fileInputStream, UTF_8);
        } catch (IOException e) {
            log.error(String.format("There is an issue with getting content from an email path: %s, stacktrace: %s", pathFile, Arrays.toString(e.getStackTrace())));
            throw new FileProcessingException();
        }
    }
}
