package wawer.kamil.beerproject.utils.upload;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.exceptions.FileProcessingException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageUploadTest {

    @Mock
    MultipartFile file;

    @InjectMocks
    ImageUpload upload;

    private static final String STANDARD_TYPE = "image/jpeg, image/jpg, image/png, image/bpm";
    private static final String CORRECT_TYPE = "image/jpeg";
    private static final String INCORRECT_TYPE = "application/pdf";
    private static final long BORDER_SIZE = 5242880L;
    private static final long CORRECT_SIZE = 4000000L;
    private static final long INCORRECT_SIZE = 1000000000L;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(upload, "standardType", STANDARD_TYPE);
        ReflectionTestUtils.setField(upload, "fileSize", BORDER_SIZE);
    }

    @Test
    @DisplayName("Should controller returns the same length of byte array as byte array from file")
    void should_controller_returns_the_same_length_of_byte_array_as_byte_array_from_file() throws IOException {
        when(file.getBytes()).thenReturn(newArray());
        byte[] bytes = newArrayWithLength(file.getBytes().length);
        int i = 0;
        for (byte b : file.getBytes()) {
            bytes[i++] = b;
        }
        assertEquals(bytes.length, upload.convertImageToByteArray(file).length);
    }

    private byte[] newArray() {
        return new byte[10];
    }

    private byte[] newArrayWithLength(int length) {
        return new byte[length];
    }

    @Test
    @DisplayName("Should throw exception when there is an issue with getting bytes from file")
    void should_throw_exception_when_there_is_an_issue_with_getting_bytes_form_file() {
        //then
        assertThrows(FileProcessingException.class, this::callconvertImageToByteArrayWithIOException);
    }

    private void callconvertImageToByteArrayWithIOException() throws IOException {
        //when
        doThrow(new IOException()).when(file).getBytes();
        upload.convertImageToByteArray(file);
    }

    @Test
    @DisplayName("Should return true if size and type are correct")
    void should_return_true_if_size_and_type_are_correct() {
        when(file.getSize()).thenReturn(CORRECT_SIZE);
        when(file.getContentType()).thenReturn(CORRECT_TYPE);
        assertTrue(upload.validateFile(file));
    }

    @Test
    @DisplayName("Should return false if size are correct and type are incorrect")
    void should_return_false_if_size_are_correct_and_type_are_incorrect() {
        when(file.getSize()).thenReturn(CORRECT_SIZE);
        when(file.getContentType()).thenReturn(INCORRECT_TYPE);
        assertFalse(upload.validateFile(file));
    }

    @Test
    @DisplayName("should return false if size are incorrect and type is correct")
    void should_return_false_if_size_are_incorrect_and_type_is_correct() {
        when(file.getSize()).thenReturn(INCORRECT_SIZE);
        when(file.getContentType()).thenReturn(CORRECT_TYPE);
        assertFalse(upload.validateFile(file));
    }

    @Test
    @DisplayName("should return false if size and type are incorrect")
    void should_return_false_if_size_and_type_are_incorrect() {
        when(file.getSize()).thenReturn(INCORRECT_SIZE);
        when(file.getContentType()).thenReturn(INCORRECT_TYPE);
        assertFalse(upload.validateFile(file));
    }
}
