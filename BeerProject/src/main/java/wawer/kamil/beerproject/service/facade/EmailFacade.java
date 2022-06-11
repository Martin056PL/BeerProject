package wawer.kamil.beerproject.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.model.email.Email;
import wawer.kamil.beerproject.model.email.factory.EmailFactory;
import wawer.kamil.beerproject.service.impl.EmailServiceImpl;

import static wawer.kamil.beerproject.model.email.factory.EmailFactory.EmailType.REGISTRATION_CONFIRMATION;
import static wawer.kamil.beerproject.model.email.factory.EmailFactory.EmailType.UPDATE_REGISTRATION_TOKEN;

@Component
@RequiredArgsConstructor
public class EmailFacade {

    private final EmailServiceImpl emailService;
    private final EmailFactory emailFactory;

    public void sendRegistrationEmail(User user) {
        sendEmail(REGISTRATION_CONFIRMATION, user);
    }

    public void sendEmailWithUpdatedToken(User user) {
        sendEmail(UPDATE_REGISTRATION_TOKEN, user);
    }

    private void sendEmail(EmailFactory.EmailType emailType, User user) {
        Email email = emailFactory.getEmail(emailType, user);
        emailService.send(email);
    }

}
