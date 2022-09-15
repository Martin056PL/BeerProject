package wawer.kamil.beerproject.utils.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.dto.response.UserRegistrationResponse;
import wawer.kamil.beerproject.model.registration.UserRegistrationData;
import wawer.kamil.beerproject.model.user.User;

@Component
@RequiredArgsConstructor
public class UserRegistrationMapper extends EntityMapper<UserRegistrationData, UserRegistrationRequest, UserRegistrationResponse> {

    public UserRegistrationResponse mapUserRegistrationEntityToUserRegistrationResponse(User user) {
        return modelMapper.map(user, UserRegistrationResponse.class);
    }
}
