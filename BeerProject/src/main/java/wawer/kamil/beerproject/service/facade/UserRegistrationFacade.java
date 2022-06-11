package wawer.kamil.beerproject.service.facade;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.model.registration.UserRegistrationData;

import static wawer.kamil.beerproject.utils.UuidGenerator.generateConfirmationToken;
import static wawer.kamil.beerproject.utils.validators.UserRegistrationValidatorClient.isUserRegistrationTokenHasBeenAlreadyConfirmed;

@Component
public class UserRegistrationFacade {

    @Value("${registration.token.expire-time}")
    private int timeToTokenExpire;

    public String refreshUserRegistrationToken(UserRegistrationData userRegistrationData) {
        isUserRegistrationTokenHasBeenAlreadyConfirmed(userRegistrationData);
        refreshRegistrationToken(userRegistrationData);
        return userRegistrationData.getConfirmationToken();
    }

    private void refreshRegistrationToken(UserRegistrationData userRegistrationData) {
        userRegistrationData.setConfirmationToken(generateConfirmationToken());
        userRegistrationData.extendExpiryDate(timeToTokenExpire);
    }

}
