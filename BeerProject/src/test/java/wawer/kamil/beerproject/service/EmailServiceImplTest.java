package wawer.kamil.beerproject.service;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import wawer.kamil.beerproject.model.email.Email;
import wawer.kamil.beerproject.service.impl.EmailServiceImpl;

import javax.mail.Message;
import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static wawer.kamil.beerproject.helpers.EmailHelper.registrationNewUserEmail;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    private final EmailServiceImpl emailService = new EmailServiceImpl(new JavaMailSenderImpl());

    private GreenMail testSmtp;

    private Email email;

    @BeforeEach
    void setUp(){
        this.testSmtp = new GreenMail(ServerSetup.SMTP);
        this.testSmtp.start();
        this.email = registrationNewUserEmail();
    }

    @Test
    @DisplayName("should send an email with required properties")
    void should_send_an_email_with_required_properties() throws MessagingException {
        //when
        emailService.send(email);
        Message[] messages = testSmtp.getReceivedMessages();

        //then
        assertEquals(1, messages.length);
        assertEquals("test subject", messages[0].getSubject());
        assertEquals("sender@email.com", messages[0].getFrom()[0].toString());
        assertEquals("receiver@email.com", messages[0].getAllRecipients()[0].toString());

        String content = GreenMailUtil.getBody(messages[0]).replaceAll("=\r?\n", "");
        assertEquals("test content", content);
    }

    @AfterEach
    public void cleanup(){
        testSmtp.stop();
    }
}
