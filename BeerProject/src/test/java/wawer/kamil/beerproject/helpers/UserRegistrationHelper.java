package wawer.kamil.beerproject.helpers;

import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.dto.response.UserRegistrationResponse;
import wawer.kamil.beerproject.model.registration.UserRegistrationData;

import static java.time.LocalDateTime.now;
import static wawer.kamil.beerproject.helpers.UserTestHelper.getUserEntity;

public class UserRegistrationHelper {

    public static UserRegistrationRequest getUserRegistrationRequest(){
        return new UserRegistrationRequest("TestUsername", "ThePassword", "test@test.com");
    }

    public static UserRegistrationResponse getUserRegistrationResponse(){
        return new UserRegistrationResponse(1L, "TestUsername", "test@test.com");
    }

    public static UserRegistrationData getUserRegistrationDataWithoutConfirmedToken(){
        return new UserRegistrationData(
                1L,
                getUserEntity(),
                "9280d346-e9af-11ec-8fea-0242ac120002",
                now(),
                false
        );
    }

    public static UserRegistrationData getUserRegistrationDataWithConfirmedToken(){
        return new UserRegistrationData(
                1L,
                getUserEntity(),
                "9280d346-e9af-11ec-8fea-0242ac120002",
                now(),
                true
        );
    }
}
