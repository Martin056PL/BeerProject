package wawer.kamil.beerproject.utils.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.dto.response.UserRegistrationResponse;
import wawer.kamil.beerproject.model.User;

@Component
@RequiredArgsConstructor
public class UserRegistrationMapper {

    private final ModelMapper modelMapper;

    public UserRegistrationResponse mapUserRegistrationEntityToUserRegistrationResponse(User user){
        return modelMapper.map(user,UserRegistrationResponse.class);
    }
}
