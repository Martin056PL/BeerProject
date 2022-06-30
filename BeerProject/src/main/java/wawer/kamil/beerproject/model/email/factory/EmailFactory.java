package wawer.kamil.beerproject.model.email.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.model.email.Email;
import wawer.kamil.beerproject.model.email.EmailType;
import wawer.kamil.beerproject.model.email.RegistrationUserConfirmationEmail;
import wawer.kamil.beerproject.model.email.UpdateRegistrationUserTokenEmail;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.utils.EnvironmentProperties;

@Component
@RequiredArgsConstructor
public class EmailFactory {

    private final EnvironmentProperties env;

    public Email getSpecificEmail(EmailType type, User user) {
        switch (type) {
            case REGISTRATION_CONFIRMATION:
                return new RegistrationUserConfirmationEmail(user, env);
            case UPDATE_REGISTRATION_TOKEN:
                return new UpdateRegistrationUserTokenEmail(user, env);
            default:
                throw new IllegalArgumentException("There is no such email type");
        }
    }
}
