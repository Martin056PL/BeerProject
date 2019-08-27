package wawer.kamil.beerproject.utils.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j(topic = "application.logger")
public class ImageUpload {

    private static final String PATH = "src/main/resources/images/.";
    private static final String STANDARD_TYPE = "image/jpeg";
    private static final long MAX_SIZE_OF_FILE = 10000000L;

    public byte[] convertFileToByteArray(MultipartFile file) throws IOException {
        byte[] byteObject = new byte[file.getBytes().length];
        int i = 0;
        for (byte b : file.getBytes()) {
            byteObject[i++] = b;
        }
        return byteObject;
    }

    public void uploadBeerImageToImagesDirectory(MultipartFile file) throws IOException {
        String path = PATH;
        File createdFile = new File(path.substring(0, path.length() - 1) + file.getOriginalFilename());
        try (FileOutputStream outputStream = new FileOutputStream(createdFile)) {
            outputStream.write(file.getBytes());
        } catch (FileNotFoundException e) {
            log.debug(e.getMessage());
        }

    }

    public boolean validateSizeAndTypeOfFile(MultipartFile file) {
        String type = file.getContentType();
        long size = file.getSize();
        int result = type.compareTo(STANDARD_TYPE);
        return result == 1 && size <= MAX_SIZE_OF_FILE;

    }
}
