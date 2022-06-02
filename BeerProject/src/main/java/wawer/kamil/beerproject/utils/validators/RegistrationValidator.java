package wawer.kamil.beerproject.utils.validators;

import lombok.NoArgsConstructor;
import wawer.kamil.beerproject.exceptions.ExpiredRegistrationTokenException;
import wawer.kamil.beerproject.exceptions.InvalidRegistrationTokenException;
import wawer.kamil.beerproject.model.UserRegistrationData;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RegistrationValidator {

    public static boolean isRegistrationTokenValid(UserRegistrationData userRegistrationData, String uuidToken) {
        boolean isTokenExpired = verifyIfTokenExpired(userRegistrationData);
        boolean isTokenAlreadyConfirmed = verifyIfTokenHasBeenConfirmed(userRegistrationData);
        boolean isTokenMatch = verifyIfTokenMatches(userRegistrationData, uuidToken);

        if (isTokenExpired || isTokenAlreadyConfirmed) {
            throw new ExpiredRegistrationTokenException();
        } else if (!isTokenMatch) {
            throw new InvalidRegistrationTokenException();
        } else {
            return true;
        }
    }

    private static boolean verifyIfTokenHasBeenConfirmed(UserRegistrationData userRegistrationData) {
        return userRegistrationData.isConfirmed();
    }

    private static boolean verifyIfTokenExpired(UserRegistrationData userRegistrationData) {
        LocalDateTime expiryDate = userRegistrationData.getExpiryDate();
        return now().isAfter(expiryDate);
    }

    private static boolean verifyIfTokenMatches(UserRegistrationData userRegistrationData, String uuidToken) {
        return uuidToken.equals(userRegistrationData.getConfirmationToken());
    }
}
