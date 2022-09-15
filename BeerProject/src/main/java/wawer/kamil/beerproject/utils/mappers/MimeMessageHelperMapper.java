package wawer.kamil.beerproject.utils.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.MimeMessageHelper;
import wawer.kamil.beerproject.exceptions.InternalException;
import wawer.kamil.beerproject.model.email.Email;

import javax.mail.MessagingException;
import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j(topic = "application.logger")
public class MimeMessageHelperMapper {

    public static MimeMessageHelper map(Email email, MimeMessageHelper helper) {
        try {
            helper.setText(email.getContent(), true);
            helper.setTo(email.getReceiver());
            helper.setFrom(email.getSender());
            helper.setSubject(email.getSubject());
        } catch (MessagingException e) {
            log.warn(String.format("There is an issue with creating and email, stacktrace: %s", Arrays.toString(e.getStackTrace())));
            throw new InternalException(e.getMessage());
        }
        return helper;
    }
}
