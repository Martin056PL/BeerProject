package wawer.kamil.beerproject.utils.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import wawer.kamil.beerproject.exceptions.InternalException;
import wawer.kamil.beerproject.model.email.Email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class MimeMessageHelperMapperTest {

    @Mock
    Email email;

    @Mock
    private MimeMessageHelper messageHelperMock;
    private MimeMessageHelper messageHelper;

    @BeforeEach
    void setEmail(){
        MimeMessage mimeMessage = new JavaMailSenderImpl().createMimeMessage();
        this.messageHelper = new MimeMessageHelper(mimeMessage);

        this.email = Mockito.mock(Email.class, Mockito.CALLS_REAL_METHODS);

        email.setSender("sernder@sender.com");
        email.setContent("Content");
        email.setSubject("Test subject");
        email.setReceiver("receiver@receiver.com");
    }

    @Test
    @DisplayName("Should return content from file")
    void should_return_content_from_file() throws MessagingException, IOException {
        //when
        MimeMessageHelper map = MimeMessageHelperMapper.map(email, messageHelper);

        //then
        assertEquals("sernder@sender.com", map.getMimeMessage().getFrom()[0].toString());
        assertEquals("Test subject", map.getMimeMessage().getSubject());
        assertEquals("Content", map.getMimeMessage().getContent());
        assertEquals("receiver@receiver.com", map.getMimeMessage().getAllRecipients()[0].toString());
    }

    @Test
    @DisplayName("Should throw an exception when path is invalid")
    void should_throw_an_internal_exception_when_the_messaging_exception_is_thrown() {
        //then
        assertThrows(InternalException.class, this::callFileWhichDoesNotExist);
    }

    private void callFileWhichDoesNotExist() throws MessagingException {
        //given
        doThrow(new MessagingException()).when(messageHelperMock).setFrom("sernder@sender.com");

        //when
        MimeMessageHelperMapper.map(email, messageHelperMock);
    }

}
