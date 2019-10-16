package wawer.kamil.beerproject.utils.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j(topic = "application.logger")
public class ImageUpload {

    @Value("${image.path}")
    private String PATH;

    @Value("${image.requirements.standard-type}")
    private String STANDARD_TYPE;

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
        boolean fileTypeResult = type.equals(STANDARD_TYPE);
        boolean fileSizeResult = size <= MAX_SIZE_OF_FILE;
        boolean result = fileTypeResult && fileSizeResult;
        log.debug("Receive files with params: type: {}, size: {}bytes; Does params are correct: {}", fileTypeResult, fileSizeResult, result);
        return result;
    }
}
