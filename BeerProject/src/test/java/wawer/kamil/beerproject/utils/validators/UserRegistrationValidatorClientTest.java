package wawer.kamil.beerproject.utils.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wawer.kamil.beerproject.exceptions.ExpiredRegistrationTokenException;
import wawer.kamil.beerproject.exceptions.InvalidRegistrationTokenException;
import wawer.kamil.beerproject.exceptions.UserAlreadyConfirmedException;
import wawer.kamil.beerproject.model.registration.UserRegistrationData;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static wawer.kamil.beerproject.helpers.UserRegistrationHelper.*;
import static wawer.kamil.beerproject.utils.validators.UserRegistrationValidatorClient.isTokenValid;
import static wawer.kamil.beerproject.utils.validators.UserRegistrationValidatorClient.validateIfUserRegistrationTokenHasBeenAlreadyConfirmed;

class UserRegistrationValidatorClientTest {

    private static final String VALID_UUID_TOKEN = "9280d346-e9af-11ec-8fea-0242ac120002";
    private static final String INVALID_UUID_TOKEN = "1f5cae4f-c64c-4e1e-bdff-0b4696eb9889";

    private UserRegistrationData validUserRegistrationData;

    private UserRegistrationData userRegistrationDataWithConfirmedToken;

    private UserRegistrationData userRegistrationDataWithExpiredToken;

    @BeforeEach
    void setup(){
        this.validUserRegistrationData = getValidUserRegistrationData();
        this.userRegistrationDataWithConfirmedToken = getUserRegistrationDataWithoutConfirmedToken();
        this.userRegistrationDataWithExpiredToken = getUserRegistrationDataWithExpiredToken();
    }

    @Test
    @DisplayName("Should return false when token has not been already confirmed")
    void should_return_false_when_token_has_not_been_already_confirmed(){
        boolean userRegistrationTokenHasBeenAlreadyConfirmed = validateIfUserRegistrationTokenHasBeenAlreadyConfirmed(validUserRegistrationData);
        Assertions.assertFalse(userRegistrationTokenHasBeenAlreadyConfirmed);
    }

    @Test
    @DisplayName("Should throw exception when token has been already confirmed")
    void should_throw_exception_when_token_has_been_already_confirmed() {
        //then
        assertThrows(UserAlreadyConfirmedException.class, this::callValidationWithTokenWhichIsAlreadyConfirm);
    }

    private void callValidationWithTokenWhichIsAlreadyConfirm() {
        //when
        validateIfUserRegistrationTokenHasBeenAlreadyConfirmed(userRegistrationDataWithConfirmedToken);
    }

    @Test
    @DisplayName("Should return true when token is valid")
    void should_return_true_when_token_is_valid(){
        boolean userRegistrationTokenIsValid = isTokenValid(validUserRegistrationData, VALID_UUID_TOKEN);
        Assertions.assertTrue(userRegistrationTokenIsValid);
    }

    @Test
    @DisplayName("Should throw exception when token has been already confirmed")
    void should_throw_exception_when_token_has_been_already_confirmed_for_valid_token() {
        //then
        assertThrows(UserAlreadyConfirmedException.class, this::callValidationForRegistrationDataWhereTokenIsAlreadyConfirmed);
    }

    private void callValidationForRegistrationDataWhereTokenIsAlreadyConfirmed() {
        //when
        isTokenValid(userRegistrationDataWithConfirmedToken, VALID_UUID_TOKEN);
    }

    @Test
    @DisplayName("Should throw exception when token is not matched")
    void should_throw_exception_when_token_is_is_not_matched() {
        //then
        assertThrows(InvalidRegistrationTokenException.class, this::callValidationForRegistrationDataWhereTokenDoesNotMatchToProvideToken);
    }

    private void callValidationForRegistrationDataWhereTokenDoesNotMatchToProvideToken() {
        //when
        isTokenValid(validUserRegistrationData, INVALID_UUID_TOKEN);
    }

    @Test
    @DisplayName("Should throw exception when token is expired")
    void should_throw_exception_when_token_is_expired() {
        //then
        assertThrows(ExpiredRegistrationTokenException.class, this::callValidationForRegistrationDataWithExpiredToken);
    }

    private void callValidationForRegistrationDataWithExpiredToken() {
        //when
        isTokenValid(userRegistrationDataWithExpiredToken, VALID_UUID_TOKEN);
    }
}
