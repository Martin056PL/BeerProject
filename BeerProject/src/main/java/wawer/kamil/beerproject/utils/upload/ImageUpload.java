package wawer.kamil.beerproject.utils.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@Service
@Slf4j(topic = "application.logger")
public class ImageUpload {

    @Value("${image.path}")
    private String path;

    @Value("${image.requirements.standard-type}")
    private String standardType;

    @Value("${image.requirements.max-file-size}")
    private Long fileSize;


    public byte[] convertImageToByteArray(MultipartFile file) throws IOException {
        byte[] byteObject = new byte[file.getBytes().length];
        int i = 0;
        for (byte b : file.getBytes()) {
            byteObject[i++] = b;
        }
        return byteObject;
    }

    public boolean validateFile(MultipartFile file) {
        String type = file.getContentType();
        long size = file.getSize();
        boolean fileTypeResult = validateFileType(type);
        boolean fileSizeResult = validateFileSize(size);
        boolean result = fileTypeResult && fileSizeResult;
        log.debug("Receive files with params: type: {}, size: {}bytes; Does params are correct: {}", fileTypeResult, fileSizeResult, result);
        return result;
    }

    private boolean validateFileType(String type) {
        return Arrays.asList(standardType.split(",")).contains(type);
    }

    private boolean validateFileSize(long size) {
        return size <= fileSize;
    }
}
