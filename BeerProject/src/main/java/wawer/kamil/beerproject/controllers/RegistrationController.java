package wawer.kamil.beerproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.dto.response.UserRegistrationResponse;
import wawer.kamil.beerproject.service.RegistrationService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<UserRegistrationResponse> signUpNewUser(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {
        return ResponseEntity.status(CREATED).body(registrationService.registerNewUser(userRegistrationRequest));
    }

    @GetMapping("/refreshToken")
    public ResponseEntity<String> refreshRegistrationToken(@RequestParam(name = "id") Long userId) {
        return ResponseEntity.status(OK).body(registrationService.refreshRegistrationToken(userId));
    }

    @GetMapping("/confirmRegistration")
    public ResponseEntity<Object> confirmAccount(@RequestParam(name = "id") Long userId, @RequestParam(name = "token") String uuidToken) {
        registrationService.confirmCredentials(userId, uuidToken);
        return ResponseEntity.status(OK).build();
    }
}
