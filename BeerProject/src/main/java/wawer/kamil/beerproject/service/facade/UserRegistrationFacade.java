package wawer.kamil.beerproject.service.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.model.registration.UserRegistrationData;
import wawer.kamil.beerproject.utils.TimeSetter;
import wawer.kamil.beerproject.utils.UuidProvider;

import static wawer.kamil.beerproject.utils.validators.UserRegistrationValidatorClient.validateIfUserRegistrationTokenHasBeenAlreadyConfirmed;

@Component
@RequiredArgsConstructor
public class UserRegistrationFacade {

    @Value("${registration.token.expire-time}")
    private int timeToTokenExpire;

    private final TimeSetter timeSetter;
    private final UuidProvider uuidProvider;

    public String refreshUserRegistrationToken(UserRegistrationData userRegistrationData) {
        validateIfUserRegistrationTokenHasBeenAlreadyConfirmed(userRegistrationData);
        refreshRegistrationToken(userRegistrationData);
        return userRegistrationData.getConfirmationToken();
    }

    private void refreshRegistrationToken(UserRegistrationData userRegistrationData) {
        userRegistrationData.setConfirmationToken(uuidProvider.getUuid().toString());
        userRegistrationData.extendExpiryDate(timeToTokenExpire, timeSetter.getNow());
    }

}
