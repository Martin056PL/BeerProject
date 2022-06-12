package wawer.kamil.beerproject.utils.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wawer.kamil.beerproject.exceptions.UserAlreadyConfirmedException;
import wawer.kamil.beerproject.model.registration.UserRegistrationData;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static wawer.kamil.beerproject.helpers.UserRegistrationHelper.getUserRegistrationDataWithConfirmedToken;
import static wawer.kamil.beerproject.helpers.UserRegistrationHelper.getUserRegistrationDataWithoutConfirmedToken;
import static wawer.kamil.beerproject.utils.validators.UserRegistrationValidatorClient.validateIfUserRegistrationTokenHasBeenAlreadyConfirmed;

class UserRegistrationValidatorClientTest {

    private UserRegistrationData userRegistrationDataWithoutConfirmedToken;
    private UserRegistrationData userRegistrationDataWithConfirmedToken;

    @BeforeEach
    void setup(){
        this.userRegistrationDataWithoutConfirmedToken = getUserRegistrationDataWithoutConfirmedToken();
        this.userRegistrationDataWithConfirmedToken = getUserRegistrationDataWithConfirmedToken();
    }

    @Test
    @DisplayName("Should return false when token has not been already confirmed")
    void should_return_false_when_token_has_not_been_already_confirmed(){
        boolean userRegistrationTokenHasBeenAlreadyConfirmed = validateIfUserRegistrationTokenHasBeenAlreadyConfirmed(userRegistrationDataWithoutConfirmedToken);
        Assertions.assertFalse(userRegistrationTokenHasBeenAlreadyConfirmed);
    }

    @Test
    @DisplayName("Should throw exception when token has been already confirmed")
    void should_throw_exception_when_token_has_been_already_confirmed() {
        //then
        assertThrows(UserAlreadyConfirmedException.class, this::callFileWhichDoesNotExist);
    }

    private void callFileWhichDoesNotExist() {
        //when
        validateIfUserRegistrationTokenHasBeenAlreadyConfirmed(userRegistrationDataWithConfirmedToken);
    }
}
