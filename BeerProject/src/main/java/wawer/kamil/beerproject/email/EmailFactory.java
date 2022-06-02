package wawer.kamil.beerproject.email;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wawer.kamil.beerproject.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailFactory {

    public static Email getEmail(EmailType type, User user) {
        switch (type) {
            case REGISTRATION_CONFIRMATION:
                return new RegistrationConfirmationEmail(user);
            case UPDATE_REGISTRATION_TOKEN:
                return new UpdateRegistrationTokenEmail(user);
            default:
                throw new IllegalArgumentException("There is no such email type");
        }
    }

}
