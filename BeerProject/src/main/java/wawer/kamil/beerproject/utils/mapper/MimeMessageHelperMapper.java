package wawer.kamil.beerproject.utils.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.MimeMessageHelper;
import wawer.kamil.beerproject.email.Email;
import wawer.kamil.beerproject.exceptions.InternalException;

import javax.mail.MessagingException;
import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class MimeMessageHelperMapper {

    public static MimeMessageHelper map(Email email, MimeMessageHelper helper){
        try {
            helper.setText(email.getContent(), true);
            helper.setTo(email.getReceiver());
            helper.setFrom(email.getSender());
            helper.setSubject(email.getSubject());
        } catch (MessagingException e) {
            log.error(String.format("There is an issue with creating and email, stacktrace: %s", Arrays.toString(e.getStackTrace())));
            throw new InternalException();
        }
        return helper;
    }
}
