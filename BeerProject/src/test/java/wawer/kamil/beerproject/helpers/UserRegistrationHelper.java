package wawer.kamil.beerproject.helpers;

import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.dto.response.UserRegistrationResponse;
import wawer.kamil.beerproject.model.registration.UserRegistrationData;
import wawer.kamil.beerproject.model.user.User;

import static java.time.LocalDateTime.now;
import static wawer.kamil.beerproject.helpers.UserTestHelper.getUserEntity;

public class UserRegistrationHelper {

    private static User user = getUserEntity();

    public static UserRegistrationRequest getUserRegistrationRequest(){
        return new UserRegistrationRequest("user", "user", "user@email.com");
    }

    public static UserRegistrationResponse getUserRegistrationResponse(){
        return new UserRegistrationResponse(1L, "user", "user@email.com");
    }

    public static UserRegistrationData getValidUserRegistrationData(){
        return new UserRegistrationData(
                1L,
                user,
                "9280d346-e9af-11ec-8fea-0242ac120002",
                now().plusMinutes(15),
                false
        );
    }

    public static UserRegistrationData getUserRegistrationDataWithoutConfirmedToken(){
        return new UserRegistrationData(
                1L,
                user,
                "9280d346-e9af-11ec-8fea-0242ac120002",
                now().plusMinutes(15),
                true
        );
    }

    public static UserRegistrationData getUserRegistrationDataWithExpiredToken(){
        return new UserRegistrationData(
                1L,
                user,
                "9280d346-e9af-11ec-8fea-0242ac120002",
                now().minusMinutes(15),
                false
        );
    }

}
