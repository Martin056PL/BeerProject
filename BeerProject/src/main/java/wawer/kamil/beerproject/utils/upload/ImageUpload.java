package wawer.kamil.beerproject.utils.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.exceptions.FileProcessingException;

import java.io.IOException;
import java.util.Arrays;

@Service
@Slf4j(topic = "application.logger")
public class ImageUpload {

    @Value("${image.requirements.standard-type}")
    private String standardType;

    @Value("${image.requirements.max-file-size}")
    private Long fileSize;


    public byte[] convertImageToByteArray(MultipartFile file) {
        try {
            return copyAndGetBytesFromFileToArray(file);
        } catch (IOException ex) {
            log.error(String.format("Something goes wrong with delivered file: \n %s", Arrays.toString(ex.getStackTrace())));
            throw new FileProcessingException();
        }
    }

    public boolean validateFile(MultipartFile file) {
        boolean result = isFileTypeValid(file) && isFileSizeValid(file);
        log.debug("Receive files with params: type: {}, size: {}bytes; Does params are correct: {}", isFileTypeValid(file), isFileSizeValid(file), result);
        return result;
    }

    private boolean isFileTypeValid(MultipartFile file) {
        String type = file.getContentType();
        return Arrays.asList(standardType.split(",")).contains(type);
    }

    private boolean isFileSizeValid(MultipartFile file) {
        long size = file.getSize();
        return size <= fileSize;
    }

    private byte[] copyAndGetBytesFromFileToArray(MultipartFile file) throws IOException {
        byte[] byteArray = new byte[file.getBytes().length];
        int i = 0;
        for (byte b : file.getBytes()) {
            byteArray[i++] = b;
        }
        return byteArray;
    }
}
