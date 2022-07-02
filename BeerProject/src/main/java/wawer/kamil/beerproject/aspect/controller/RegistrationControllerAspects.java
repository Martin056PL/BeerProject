package wawer.kamil.beerproject.aspect.controller;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;

@Aspect
@Component
@Slf4j(topic = "application.logger")
public class RegistrationControllerAspects {

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.RegistrationController.signUpNewUser(..)) && args(userRegistrationRequest)")
    public void logSignUpNewUser(UserRegistrationRequest userRegistrationRequest) {
        log.debug("Endpoint address: 'registration' with POST method, request parameter - userRegistrationRequest: {}", userRegistrationRequest);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.RegistrationController.refreshRegistrationToken(..)) && args(userId)")
    public void logRefreshRegistrationToken(Long userId) {
        log.debug("Endpoint address: 'registration/refreshToken' with GET method, request parameter - user ID: {}", userId);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.RegistrationController.confirmAccount(..)) && args(userId, uuidToken)", argNames = "userId,uuidToken")
    public void logConfirmAccount(Long userId, String uuidToken) {
        log.debug("Endpoint address: 'registration/confirmRegistration' with GET method, request parameter - userId: {}, token: {}", userId, uuidToken);
    }

}
