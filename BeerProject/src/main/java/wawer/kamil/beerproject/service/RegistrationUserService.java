package wawer.kamil.beerproject.service;

import org.springframework.transaction.annotation.Transactional;
import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.dto.response.UserRegistrationResponse;

public interface RegistrationUserService {
    @Transactional
    UserRegistrationResponse registerNewUser(UserRegistrationRequest request);

    @Transactional
    String refreshRegistrationToken(Long userId);

    @Transactional
    void confirmCredentials(Long userId, String uuidToken);
}
