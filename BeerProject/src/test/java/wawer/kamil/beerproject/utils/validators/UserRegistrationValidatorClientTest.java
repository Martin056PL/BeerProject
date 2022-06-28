package wawer.kamil.beerproject.utils.validators;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wawer.kamil.beerproject.exceptions.ExpiredRegistrationTokenException;
import wawer.kamil.beerproject.exceptions.InvalidRegistrationTokenException;
import wawer.kamil.beerproject.exceptions.UserAlreadyConfirmedException;
import wawer.kamil.beerproject.model.registration.UserRegistrationData;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static wawer.kamil.beerproject.helpers.UserRegistrationHelper.*;
import static wawer.kamil.beerproject.utils.validators.UserRegistrationValidatorClient.isTokenValid;
import static wawer.kamil.beerproject.utils.validators.UserRegistrationValidatorClient.validateIfUserRegistrationTokenHasBeenAlreadyConfirmed;

class UserRegistrationValidatorClientTest {

    private static final String VALID_UUID_TOKEN = "9280d346-e9af-11ec-8fea-0242ac120002";
    private static final String INVALID_UUID_TOKEN = "1f5cae4f-c64c-4e1e-bdff-0b4696eb9889";

    private static final Clock clock = Clock.fixed(Instant.parse("2022-01-01T20:20:20.000Z"), ZoneId.systemDefault());

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
        //when
        boolean userRegistrationTokenIsValid = isTokenValid(validUserRegistrationData, clock, VALID_UUID_TOKEN);

        //then
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
        isTokenValid(userRegistrationDataWithConfirmedToken, clock, VALID_UUID_TOKEN);
    }

    @Test
    @DisplayName("Should throw exception when token is not matched")
    void should_throw_exception_when_token_is_is_not_matched() {
        //then
        assertThrows(InvalidRegistrationTokenException.class, this::callValidationForRegistrationDataWhereTokenDoesNotMatchToProvideToken);
    }

    private void callValidationForRegistrationDataWhereTokenDoesNotMatchToProvideToken() {
        //when
        isTokenValid(validUserRegistrationData, clock, INVALID_UUID_TOKEN);
    }

    @Test
    @DisplayName("Should throw exception when token is expired")
    void should_throw_exception_when_token_is_expired() {
        //then
        assertThrows(ExpiredRegistrationTokenException.class, this::callValidationForRegistrationDataWithExpiredToken);
    }

    private void callValidationForRegistrationDataWithExpiredToken() {
        //when
        isTokenValid(userRegistrationDataWithExpiredToken, clock, VALID_UUID_TOKEN);
    }
}
