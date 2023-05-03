package wawer.kamil.beerproject.helpers;

import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.dto.response.UserRegistrationResponse;
import wawer.kamil.beerproject.model.registration.UserRegistrationData;
import wawer.kamil.beerproject.model.user.User;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static wawer.kamil.beerproject.helpers.UserTestHelper.getUserEntityWithUserRole;

public class UserRegistrationHelper {

    private final static User user = getUserEntityWithUserRole();

    public static UserRegistrationRequest getUserRegistrationRequest() {
        return new UserRegistrationRequest("user", "user", "user@email.com");
    }

    public static UserRegistrationResponse getUserRegistrationResponse() {
        return new UserRegistrationResponse(1L, "user", "user@email.com");
    }

    public static UserRegistrationData getValidUserRegistrationData() {
        return null;
    }

    public static UserRegistrationData getUserRegistrationDataWithoutConfirmedToken() {
        return null;
    }

    public static UserRegistrationData getUserRegistrationDataWithoutConfirmedTokenAndDifferentUuid() {
        return null;
    }

    public static UserRegistrationData getUserRegistrationDataWithExpiredToken() {
        return null;
    }

    private static Clock getClock() {
        return Clock.fixed(Instant.parse("2022-01-01T20:20:20.000Z"), ZoneId.systemDefault());
    }

}
