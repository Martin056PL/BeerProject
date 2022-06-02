package wawer.kamil.beerproject.utils.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;
    private final ModelMapper modelMapperForUserToUser;

    public UserMapper(@Qualifier(value = "defaultMapper") ModelMapper modelMapper,
                      @Qualifier(value = "UserMapper") ModelMapper modelMapperForUserToUser) {
        this.modelMapper = modelMapper;
        this.modelMapperForUserToUser = modelMapperForUserToUser;
    }

    public UserResponse mapUserEntityToUserResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public Page<UserResponse> mapUserEntityPageToUserResponsePage(Page<User> userPage) {
        return userPage.map(user -> modelMapper.map(user, UserResponse.class));
    }

    public List<UserResponse> mapUserEntityListToUserResponseList(List<User> userList) {
        return userList.stream().map(user -> modelMapper.map(user, UserResponse.class)).collect(Collectors.toList());
    }

    public User mapUserRequestToUserEntity(UserRequest userRequest) {
        return modelMapper.map(userRequest, User.class);
    }

    public User mapUserRequestToUserEntity(UserRequest userRequest, User userEntity) {
        modelMapperForUserToUser.map(userRequest, userEntity);
        return userEntity;
    }
}