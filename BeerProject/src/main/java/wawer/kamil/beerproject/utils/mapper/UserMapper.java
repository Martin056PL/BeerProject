package wawer.kamil.beerproject.utils.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserResponse mapUserToUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public Page<UserResponse> mapUserPageToUserResponsePage(Page<User> userPage) {
        return userPage.map(user -> modelMapper.map(user, UserResponse.class));
    }

    public List<UserResponse> mapUserListToUserResponseList(List<User> userList) {
        return userList.stream().map(user -> modelMapper.map(user, UserResponse.class)).collect(Collectors.toList());
    }

    public User mapUserRequestToUserEntity(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }
}
