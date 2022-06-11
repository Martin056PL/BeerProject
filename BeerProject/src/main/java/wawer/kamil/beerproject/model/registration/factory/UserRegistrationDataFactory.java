package wawer.kamil.beerproject.model.registration.factory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.model.registration.UserRegistrationData;

@Component
public class UserRegistrationDataFactory {

    @Value("${registration.token.expire-time}")
    private int timeToTokenExpire;

    public UserRegistrationData getUserRegistrationData(){
        return new UserRegistrationData(timeToTokenExpire);
    }

}
