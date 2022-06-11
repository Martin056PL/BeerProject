package wawer.kamil.beerproject.aspect.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.dto.response.UserRegistrationResponse;

@Aspect
@Component
@Slf4j(topic = "application.logger")
public class RegistrationServiceAspects {

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.RegistrationUserServiceImpl.registerNewUser(..))", returning = "userRegistrationResponse")
    public void logRegisterNewUser(UserRegistrationResponse userRegistrationResponse) {
        log.debug("Returned userRegistrationResponse: {}", userRegistrationResponse);
    }

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.RegistrationUserServiceImpl.refreshRegistrationToken(..))", returning = "token")
    public void logRefreshRegistrationToken(String token) {
        log.debug("Returned updated token: {}", token);
    }
}
