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
    private static final long ACCEPT_SIZE = 10L;
    private static final long WRONG_SIZE = 1000000000L;
    private static final String BAD_TYPE = "CONTEXT";

    @Test
    public void should_return_true_when_controller_returns_the_same_length_of_byte_array_as_byte_array_from_file() throws IOException {
        when(file.getBytes()).thenReturn(newArray());
        byte [] bytes = newArrayWithLength(file.getBytes().length);
        int i = 0;
        for (byte b : file.getBytes()) {
            bytes[i++] = b;
        }
        assertEquals(bytes.length,upload.convertFileToByteArray(file).length);
    }

    @Test
    public void should_return_true_if_size_and_type_are_correct(){
        when(file.getSize()).thenReturn(ACCEPT_SIZE);
        when(file.getContentType()).thenReturn(STANDARD_TYPE);
        assertTrue(upload.validateSizeAndTypeOfFile(file));
    }

    @Test
    public void should_return_false_if_size_are_correct_and_type_are_incorrect(){
        when(file.getSize()).thenReturn(ACCEPT_SIZE);
        when(file.getContentType()).thenReturn(BAD_TYPE);
        assertFalse(upload.validateSizeAndTypeOfFile(file));
    }

    @Test
    public void should_return_false_if_size_are_incorrect_and_type_is_correct(){
        when(file.getSize()).thenReturn(WRONG_SIZE);
        when(file.getContentType()).thenReturn(STANDARD_TYPE);
        assertFalse(upload.validateSizeAndTypeOfFile(file));
    }

    @Test
    public void should_return_false_if_size_and_type_are_incorrect(){
        when(file.getSize()).thenReturn(WRONG_SIZE);
        when(file.getContentType()).thenReturn(BAD_TYPE);
        assertFalse(upload.validateSizeAndTypeOfFile(file));
    }

    private byte [] newArray(){
        return new byte [10];
    }

    private byte [] newArrayWithLength(int length){
        return new byte [length];
    }
}
