package wawer.kamil.beerproject.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.exceptions.UsernameAlreadyExistsException;
import wawer.kamil.beerproject.model.User;
import wawer.kamil.beerproject.repositories.UserRepository;
import wawer.kamil.beerproject.service.UserService;
import wawer.kamil.beerproject.utils.mapper.UserMapper;

import java.util.List;

@Service(value = "UserServiceImpl")
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findAllByUsername(username);
    }

    @Override
    public Page<UserResponse> findAllUsersPage(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userMapper.mapUserPageToUserResponsePage(userPage);
    }

    @Override
    public List<UserResponse> findAllUsersList() {
        List<User> userList = userRepository.findAll();
        return userMapper.mapUserListToUserResponseList(userList);
    }

    @Override
    public UserResponse findUserByUserId(Long userId) throws NoContentException {
        User user = userRepository.findById(userId).orElseThrow(NoContentException::new);
        return userMapper.mapUserToUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse addNewUser(UserRequest userRequest) throws UsernameAlreadyExistsException {
        if (isUsernameExistInDatabase(userRequest.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }
        User user = userMapper.mapUserRequestToUserEntityAsDefaultMethod(userRequest);
        User savedUser = userRepository.save(user);
        return userMapper.mapUserToUserResponse(savedUser);
    }

    private boolean isUsernameExistInDatabase(String username) {
        return userRepository.existsUserByUsername(username);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long userId, UserRequest userRequest) throws NoContentException {
        User user = userRepository.findById(userId).orElseThrow(NoContentException::new);
        userMapper.mapUserRequestToUserEntityForUpdateMethod(userRequest, user);
        return userMapper.mapUserToUserResponse(user);
    }
}
