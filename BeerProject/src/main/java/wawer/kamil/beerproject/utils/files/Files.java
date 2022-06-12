package wawer.kamil.beerproject.utils.files;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import wawer.kamil.beerproject.exceptions.FileProcessingException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j(topic = "application.logger")
public class Files {

    public static String getEmailContent(String pathFile) {
        try {
            Resource resource = new ClassPathResource(String.format(pathFile));
            InputStream inputStream = resource.getInputStream();
            return IOUtils.toString(inputStream, UTF_8);
        } catch (IOException e) {
            log.warn(String.format("There is an issue with getting content from an email path: %s, stacktrace: %s", pathFile, Arrays.toString(e.getStackTrace())));
            throw new FileProcessingException();
        }
    }
}
