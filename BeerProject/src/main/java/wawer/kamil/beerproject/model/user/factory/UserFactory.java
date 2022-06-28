package wawer.kamil.beerproject.model.user.factory;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.configuration.security.ApplicationUserRole;
import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.exceptions.InternalException;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.model.user.UserDetailsHelper;
import wawer.kamil.beerproject.model.registration.UserRegistrationData;
import wawer.kamil.beerproject.model.registration.factory.UserRegistrationDataFactory;

import static wawer.kamil.beerproject.configuration.security.ApplicationUserRole.*;

@Component
@RequiredArgsConstructor
public class UserFactory {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRegistrationDataFactory userRegistrationDataFactory;

    public User createNewUser(UserRegistrationRequest request, ApplicationUserRole role) {
        switch (role) {
            case USER:
                return createNewUserWithRegistrationData(request, USER);
            case ADMIN:
                return createNewUserWithRegistrationData(request, ADMIN);
            case EXHIBITOR:
                return createNewUserWithRegistrationData(request, EXHIBITOR);
            default:
                throw new InternalException("Provided user must have his own logic for creation!");
        }
    }

    private User createNewUserWithRegistrationData(UserRegistrationRequest request, ApplicationUserRole role) {
        UserRegistrationData userRegistrationData = userRegistrationDataFactory.getUserRegistrationData();
        return new User(
                request.getUsername(),
                encryptUsernamePassword(request.getPassword()),
                request.getEmail(),
                getUserDetailsHelperForNewUser(role)
        ).withRegistrationData(userRegistrationData);
    }

    private String encryptUsernamePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private UserDetailsHelper getUserDetailsHelperForNewUser(ApplicationUserRole role) {
        return new UserDetailsHelper(
                role.getGrantedAuthority(),
                true,
                true,
                true,
                false
        );
    }
}
