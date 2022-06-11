package wawer.kamil.beerproject.model.email.factory;

import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.model.email.Email;
import wawer.kamil.beerproject.model.email.RegistrationUserConfirmationEmail;
import wawer.kamil.beerproject.model.email.UpdateRegistrationUserTokenEmail;
import wawer.kamil.beerproject.model.user.User;

@Component
public class EmailFactory {

    public Email getEmail(EmailType type, User user) {
        switch (type) {
            case REGISTRATION_CONFIRMATION:
                return new RegistrationUserConfirmationEmail(user);
            case UPDATE_REGISTRATION_TOKEN:
                return new UpdateRegistrationUserTokenEmail(user);
            default:
                throw new IllegalArgumentException("There is no such email type");
        }
    }

    public enum EmailType {
        REGISTRATION_CONFIRMATION,
        UPDATE_REGISTRATION_TOKEN
    }
}
