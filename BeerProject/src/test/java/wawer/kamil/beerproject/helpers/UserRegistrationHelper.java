package wawer.kamil.beerproject.helpers;

import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.dto.response.UserRegistrationResponse;

public class UserRegistrationHelper {

    public static UserRegistrationRequest getUserRegistrationRequest(){
        return new UserRegistrationRequest("TestUsername", "ThePassword", "test@test.com");
    }

    public static UserRegistrationResponse getUserRegistrationResponse(){
        return new UserRegistrationResponse(1L, "TestUsername", "test@test.com");
    }
}
