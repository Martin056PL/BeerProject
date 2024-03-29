package wawer.kamil.beerproject.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.model.email.Email;
import wawer.kamil.beerproject.model.email.EmailType;
import wawer.kamil.beerproject.model.email.factory.EmailFactory;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.service.EmailService;

import static wawer.kamil.beerproject.model.email.EmailType.REGISTRATION_CONFIRMATION;
import static wawer.kamil.beerproject.model.email.EmailType.UPDATE_REGISTRATION_TOKEN;

@Component
@RequiredArgsConstructor
public class EmailFacade {

    private final EmailService emailService;
    private final EmailFactory emailFactory;

    public void sendRegistrationEmail(User user) {
        sendEmail(REGISTRATION_CONFIRMATION, user);
    }

    public void sendEmailWithUpdatedToken(User user) {
        sendEmail(UPDATE_REGISTRATION_TOKEN, user);
    }

    private void sendEmail(EmailType emailType, User user) {
        Email email = emailFactory.getSpecificEmail(emailType, user);
        emailService.send(email);
    }
}
