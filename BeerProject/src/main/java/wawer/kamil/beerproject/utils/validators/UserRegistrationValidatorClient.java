package wawer.kamil.beerproject.utils.validators;

import lombok.NoArgsConstructor;
import wawer.kamil.beerproject.exceptions.ExpiredRegistrationTokenException;
import wawer.kamil.beerproject.exceptions.InvalidRegistrationTokenException;
import wawer.kamil.beerproject.exceptions.UserAlreadyConfirmedException;
import wawer.kamil.beerproject.model.registration.UserRegistrationData;

import java.time.Clock;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserRegistrationValidatorClient {
    public static boolean validateIfUserRegistrationTokenHasBeenAlreadyConfirmed(UserRegistrationData userRegistrationData) {
        UserRegistrationValidator.ValidationResult validationResult = UserRegistrationValidator.isTokenConfirmed().apply(userRegistrationData);

        if (validationResult == UserRegistrationValidator.ValidationResult.TOKEN_ALREADY_CONFIRMED) {
            throw new UserAlreadyConfirmedException();
        }

        return false;
    }

    public static boolean isTokenValid(UserRegistrationData userRegistrationData, Clock clock, String uuidToken) {
        UserRegistrationValidator.ValidationResult validationResult =
                UserRegistrationValidator.isTokenConfirmed()
                        .and(UserRegistrationValidator.isTokenExpired(clock))
                        .and(UserRegistrationValidator.isTokenMatches(uuidToken))
                        .apply(userRegistrationData);

        switch (validationResult){
            case TOKEN_DOES_NOT_MATCH:
                throw new InvalidRegistrationTokenException();
            case TOKEN_ALREADY_CONFIRMED:
                throw new UserAlreadyConfirmedException();
            case TOKEN_EXPIRED:
                throw new ExpiredRegistrationTokenException();
            default:
                return true;
        }
    }
}
