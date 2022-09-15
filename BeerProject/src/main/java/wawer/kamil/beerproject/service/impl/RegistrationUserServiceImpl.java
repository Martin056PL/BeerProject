package wawer.kamil.beerproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.dto.response.UserRegistrationResponse;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.service.RegistrationUserService;
import wawer.kamil.beerproject.service.UserService;
import wawer.kamil.beerproject.service.facade.EmailFacade;
import wawer.kamil.beerproject.service.facade.UserRegistrationFacade;
import wawer.kamil.beerproject.utils.mappers.UserRegistrationMapper;

import java.time.Clock;

import static wawer.kamil.beerproject.configuration.security.ApplicationUserRole.USER;
import static wawer.kamil.beerproject.utils.validators.UserRegistrationValidatorClient.isTokenValid;

@Service
@RequiredArgsConstructor
public class RegistrationUserServiceImpl implements RegistrationUserService {

    private final UserService userService;
    private final UserRegistrationFacade userRegistrationFacade;
    private final EmailFacade emailFacade;
    private final UserRegistrationMapper mapper;

    private final Clock clock;

    @Transactional
    @Override
    public UserRegistrationResponse registerNewUser(UserRegistrationRequest userRegistrationRequest) {
        User newUserWithRegistrationData = userService.getUserWithUserRegistrationData(userRegistrationRequest, USER);
        User savedUser = userService.saveUser(newUserWithRegistrationData);
        emailFacade.sendRegistrationEmail(savedUser);
        return mapper.mapUserRegistrationEntityToUserRegistrationResponse(savedUser);
    }

    @Transactional
    @Override
    public String refreshRegistrationToken(Long userId) {
        User user = userService.findUserById(userId);
        String refreshedConfirmationToken = userRegistrationFacade.refreshUserRegistrationToken(user.getUserRegistrationData());
        emailFacade.sendEmailWithUpdatedToken(user);
        return refreshedConfirmationToken;

    }

    @Transactional
    @Override
    public void confirmCredentials(Long userId, String uuidTokenFromRequest) {
        User user = userService.findUserById(userId);
        if (isTokenValid(user.getUserRegistrationData(), clock, uuidTokenFromRequest)) {
            userService.enableUserAccount(user);
        }
    }
}
