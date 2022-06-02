package wawer.kamil.beerproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.dto.response.UserRegistrationResponse;
import wawer.kamil.beerproject.email.Email;
import wawer.kamil.beerproject.email.EmailFactory;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.UserAlreadyConfirmedException;
import wawer.kamil.beerproject.exceptions.UsernameAlreadyExistsException;
import wawer.kamil.beerproject.model.User;
import wawer.kamil.beerproject.model.UserRegistrationData;
import wawer.kamil.beerproject.model.helpers.UserDetailsHelper;
import wawer.kamil.beerproject.repositories.UserRepository;
import wawer.kamil.beerproject.service.RegistrationService;
import wawer.kamil.beerproject.utils.mapper.UserRegistrationMapper;

import static java.time.LocalDateTime.now;
import static wawer.kamil.beerproject.configuration.security.ApplicationUserRole.USER;
import static wawer.kamil.beerproject.email.EmailType.REGISTRATION_CONFIRMATION;
import static wawer.kamil.beerproject.email.EmailType.UPDATE_REGISTRATION_TOKEN;
import static wawer.kamil.beerproject.utils.UuidGenerator.generateConfirmationToken;
import static wawer.kamil.beerproject.utils.validators.RegistrationValidator.isRegistrationTokenValid;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final EmailServiceImpl emailService;
    private final UserRegistrationMapper mapper;

    @Transactional
    @Override
    public UserRegistrationResponse registerNewUser(UserRegistrationRequest request) {
        throwExceptionWhenUsernameIsAlreadyInUse(request);
        User newUserWithRegistrationData = createNewUserWithRegistrationData(request);
        User savedUser = userRepository.save(newUserWithRegistrationData);
        Email email = EmailFactory.getEmail(REGISTRATION_CONFIRMATION, savedUser);
        emailService.send(email);
        return mapper.mapUserRegistrationEntityToUserRegistrationResponse(savedUser);
        // TODO add aspect as logs
    }

    @Transactional
    @Override
    public String refreshRegistrationToken(Long userId) {
        User user = getUserById(userId);
        UserRegistrationData userRegistrationData = user.getUserRegistrationData();

        if (isUserRegistrationTokenHasBeenAlreadyConfirmed(userRegistrationData)) {
            throw new UserAlreadyConfirmedException();
        }

        refreshRegistrationToken(userRegistrationData);
        Email email = EmailFactory.getEmail(UPDATE_REGISTRATION_TOKEN, user);
        emailService.send(email);
        return userRegistrationData.getConfirmationToken();
        // TODO add aspect as logs
    }

    @Transactional
    @Override
    public void confirmCredentials(Long userId, String uuidToken) {
        UserRegistrationData userRegistrationData = getUserById(userId).getUserRegistrationData();
        if (isRegistrationTokenValid(userRegistrationData, uuidToken)) {
            enableUserAccount(userRegistrationData.getUser());
        }
        // TODO add aspect as logs
    }

    private void throwExceptionWhenUsernameIsAlreadyInUse(UserRegistrationRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent(user -> {
            throw new UsernameAlreadyExistsException();
        });
    }

    private User createNewUserWithRegistrationData(UserRegistrationRequest request) {
        return new User(
                request.getUsername(),
                encryptUsernamePassword(request.getPassword()),
                request.getEmail(),
                getUserDetailsHelperForNewUser()
        ).withRegistrationData(new UserRegistrationData());
    }

    private String encryptUsernamePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private UserDetailsHelper getUserDetailsHelperForNewUser() {
        return new UserDetailsHelper(
                USER.getGrantedAuthority(),
                true,
                true,
                true,
                false
        );
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(ElementNotFoundException::new);
    }

    private boolean isUserRegistrationTokenHasBeenAlreadyConfirmed(UserRegistrationData userRegistrationData) {
        return userRegistrationData.isConfirmed();
    }

    private void refreshRegistrationToken(UserRegistrationData userRegistrationData) {
        userRegistrationData.setConfirmationToken(generateConfirmationToken());
        userRegistrationData.setExpiryDate(now());
        userRegistrationData.setUpdatedAt(now());
    }

    private void enableUserAccount(User user) {
        user.setEnabled(true);
        user.getUserRegistrationData().setConfirmed(true);
        user.setUpdatedAt(now());
    }
}
