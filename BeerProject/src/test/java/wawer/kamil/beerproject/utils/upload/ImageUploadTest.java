package wawer.kamil.beerproject.utils.upload;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImageUploadTest {

    @Mock
    MultipartFile file;

    @InjectMocks
    ImageUpload upload;

    private static final String STANDARD_TYPE = "image/jpeg";;
    private static final long ACCEPTSIZE = 10L;
    private static final long WRONGSIZE = 1000000000L;
    private static final String BAD_TYPE = "CONTEXT";

    @Test
    public void asd() throws IOException {
        when(file.getBytes()).thenReturn(newArray());
        byte [] bytes = newArrayWithLength(file.getBytes().length);
        int i = 0;
        for (byte b : file.getBytes()) {
            bytes[i++] = b;
        }
        assertEquals(bytes.length,upload.convertFileToByteArray(file).length);
    }

    @Test
    public void good_good(){
        when(file.getSize()).thenReturn(ACCEPTSIZE);
        when(file.getContentType()).thenReturn(STANDARD_TYPE);
        assertTrue(upload.validateSizeAndTypeOfFile(file));
    }

    @Test
    public void good_bad(){
        when(file.getSize()).thenReturn(ACCEPTSIZE);
        when(file.getContentType()).thenReturn(BAD_TYPE);
        assertFalse(upload.validateSizeAndTypeOfFile(file));
    }

    @Test
    public void bad_good(){
        when(file.getSize()).thenReturn(WRONGSIZE);
        when(file.getContentType()).thenReturn(STANDARD_TYPE);
        assertFalse(upload.validateSizeAndTypeOfFile(file));
    }

    @Test
    public void bad_bad(){
        when(file.getSize()).thenReturn(WRONGSIZE);
        when(file.getContentType()).thenReturn(BAD_TYPE);
        assertFalse(upload.validateSizeAndTypeOfFile(file));
    }

    private byte [] newArray(){
        byte [] ds = new byte [10];
        return ds;
    }

    private byte [] newArrayWithLength(int length){
        byte [] ds = new byte [length];
        return ds;
    }
}
