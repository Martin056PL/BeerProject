package wawer.kamil.beerproject.model.email.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.model.email.*;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.utils.link.LinkFactory;
import wawer.kamil.beerproject.utils.link.LinkProvider;

import static wawer.kamil.beerproject.model.email.EmailType.REGISTRATION_CONFIRMATION;
import static wawer.kamil.beerproject.model.email.EmailType.UPDATE_REGISTRATION_TOKEN;

@Component
@RequiredArgsConstructor
public class EmailFactory {

     private final LinkFactory factory;

    public Email getEmail(EmailType type, User user) {
        Email email;
        switch (type) {
            case REGISTRATION_CONFIRMATION:
                email = new RegistrationUserConfirmationEmail(user);
                return getEmailWithLink(email, user, REGISTRATION_CONFIRMATION);
            case UPDATE_REGISTRATION_TOKEN:
                email = new UpdateRegistrationUserTokenEmail(user);
                return getEmailWithLink(email, user, UPDATE_REGISTRATION_TOKEN);
            default:
                throw new IllegalArgumentException("There is no such email type");
        }
    }

    private Email getEmailWithLink(Email email, User user, EmailType emailType) {
        return new WithLinkEmail(email, user, getLinkProvider(emailType));
    }

    private LinkProvider getLinkProvider(EmailType emailType){
        return factory.getLinkProvider(emailType);
    }
}
