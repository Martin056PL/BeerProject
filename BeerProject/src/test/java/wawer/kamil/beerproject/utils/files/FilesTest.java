package wawer.kamil.beerproject.utils.files;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wawer.kamil.beerproject.exceptions.FileProcessingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FilesTest {

    @Test
    @DisplayName("Should return content from file")
    void should_return_content_from_file(){
        //given
        String file = "text.html";

        //when
        String emailContent = Files.getEmailContent(file);

        //then
        assertEquals("test content", emailContent);
    }

    @Test
    @DisplayName("Should throw an exception when path is invalid")
    void should_throw_an_exception_when_path_is_invalid() {
        //then
        assertThrows(FileProcessingException.class, this::callFileWhichDoesNotExist);
    }

    private void callFileWhichDoesNotExist() {
        //given
        String path = "FileNotExist.txt";

        //when
        Files.getEmailContent(path);
    }

}
