package wawer.kamil.beerproject.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeRest {

    @GetMapping("/hello")
    public String helloEndpoint() {
        return "Hello!";
    }

    @GetMapping("/helloafterlogin")
    public String helloAfterLogin() {
        return "hello after login!";
    }

}
