package wawer.kamil.beerproject.aspect.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.model.email.Email;

@Aspect
@Component
@Slf4j(topic = "application.logger")
public class EmailServiceAspects {

    @Before(value = "execution(* wawer.kamil.beerproject.service.EmailService.send(..)) && args(email)")
    public void logGetAllBreweryPage(Email email) {
        log.debug("Email to send: sender: {}, receiver: {}, subject: {}", email.getSender(), email.getReceiver(), email.getSubject());
    }
}
