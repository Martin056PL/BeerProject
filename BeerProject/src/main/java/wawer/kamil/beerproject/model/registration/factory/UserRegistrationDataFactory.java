package wawer.kamil.beerproject.model.registration.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.model.registration.UserRegistrationData;
import wawer.kamil.beerproject.utils.TimeSetter;
import wawer.kamil.beerproject.utils.UuidProvider;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserRegistrationDataFactory {

    @Value("${registration.token.expire-time}")
    private Integer timeToTokenExpire;

    private final TimeSetter timeSetter;
    private final UuidProvider uuidProvider;

    public UserRegistrationData getUserRegistrationData() {
        LocalDateTime now = timeSetter.getNow();
        String generatedUuid = uuidProvider.getUuid().toString();
        return new UserRegistrationData(now, timeToTokenExpire, generatedUuid);
    }

}
