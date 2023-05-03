package wawer.kamil.beerproject.helpers;

import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.dto.response.UserRegistrationResponse;
import wawer.kamil.beerproject.model.registration.UserRegistrationData;
import wawer.kamil.beerproject.model.user.User;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static java.time.LocalDateTime.now;
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
        return new UserRegistrationData(
                1L,
                0L,
                user,
                "9280d346-e9af-11ec-8fea-0242ac120002",
                now(getClock()).plusMinutes(15),
                false
        );
    }

    public static UserRegistrationData getUserRegistrationDataWithoutConfirmedToken() {
        return new UserRegistrationData(
                1L,
                0L,
                user,
                "9280d346-e9af-11ec-8fea-0242ac120002",
                now(getClock()).plusMinutes(15),
                false
        );
    }

    public static UserRegistrationData getUserRegistrationDataWithoutConfirmedTokenAndDifferentUuid() {
        return new UserRegistrationData(
                1L,
                0L,
                user,
                "9280d346-e9af-11ec-8fea-0242ac120002",
                now(getClock()).plusMinutes(15),
                false
        );
    }

    public static UserRegistrationData getUserRegistrationDataWithExpiredToken() {
        return new UserRegistrationData(
                1L,
                0L,
                user,
                "9280d346-e9af-11ec-8fea-0242ac120002",
                now(getClock()).plusMinutes(15),
                false
        );
    }

    private static Clock getClock() {
        return Clock.fixed(Instant.parse("2022-01-01T20:20:20.000Z"), ZoneId.systemDefault());
    }

}
