package wawer.kamil.beerproject.utils.upload;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ImageUploadTest {

    @Mock
    MultipartFile file;

    @InjectMocks
    ImageUpload upload;

    private static final String TYPE = "TYPE";
    private static final long SIZE = 1L;
    private static final int Result = 0;

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

   /* @Test
    public void fed(){
        when(file.getContentType()).thenReturn(string());
        String asd =file.getContentType();
        when(file.getSize()).thenReturn(SIZE);
        int result = asd.compareTo(TYPE);
        when(file.getSize() <= SIZE).thenReturn(true);
        boolean aqwe = result == 0 && file.getSize() == SIZE;
        assertEquals(aqwe, upload.validateSizeAndTypeOfFile(file));
    }*/

    public String string(){
        return "asd";
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
