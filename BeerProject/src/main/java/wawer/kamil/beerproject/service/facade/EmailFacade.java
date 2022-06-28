package wawer.kamil.beerproject.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.model.email.Email;
import wawer.kamil.beerproject.model.email.EmailType;
import wawer.kamil.beerproject.model.email.decorator.EmailDecorator;
import wawer.kamil.beerproject.model.email.factory.EmailFactory;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.service.impl.EmailServiceImpl;

import static wawer.kamil.beerproject.model.email.EmailType.REGISTRATION_CONFIRMATION;
import static wawer.kamil.beerproject.model.email.EmailType.UPDATE_REGISTRATION_TOKEN;

@Component
@RequiredArgsConstructor
public class EmailFacade {

    private final EmailServiceImpl emailService;
    private final EmailFactory emailFactory;

    private final EmailDecorator emailDecorator;

    public void sendRegistrationEmail(User user) {
        sendEmail(REGISTRATION_CONFIRMATION, user);
    }

    public void sendEmailWithUpdatedToken(User user) {
        sendEmail(UPDATE_REGISTRATION_TOKEN, user);
    }

    private void sendEmail(EmailType emailType, User user) {
        Email email = emailFactory.getBasicEmail(emailType, user);
        Email emailWithLink = emailDecorator.getEmailWithLink(email, user, emailType);
        sendEmail(emailWithLink);
    }

    private void sendEmail(Email email){
        emailService.send(email);
    }

}
