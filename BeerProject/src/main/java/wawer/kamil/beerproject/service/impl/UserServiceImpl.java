package wawer.kamil.beerproject.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.UsernameAlreadyExistsException;
import wawer.kamil.beerproject.model.User;
import wawer.kamil.beerproject.repositories.UserRepository;
import wawer.kamil.beerproject.service.UserService;
import wawer.kamil.beerproject.utils.mapper.UserMapper;

import java.util.List;

import static wawer.kamil.beerproject.generators.Generator.createUser;

@Service(value = "UserServiceImpl")
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public User generateDefaultUserToDatabase() {
        return userRepository.save(createUser());
    }

    @Override
    public Page<UserResponse> findAllUsersPage(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userMapper.mapUserEntityPageToUserResponsePage(userPage);
    }

    @Override
    public List<UserResponse> findAllUsersList() {
        List<User> userList = userRepository.findAll();
        return userMapper.mapUserEntityListToUserResponseList(userList);
    }

    @Override
    public UserResponse findUserByUserId(Long userId) throws ElementNotFoundException {
        return userRepository.findById(userId)
                .map(userMapper::mapUserEntityToUserResponse)
                .orElseThrow(ElementNotFoundException::new);
    }

    @Override
    @Transactional
    public UserResponse addNewUser(UserRequest userRequest) throws UsernameAlreadyExistsException {
        if (isUsernameExistInDatabase(userRequest.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }
        User user = userMapper.mapUserRequestToUserEntity(userRequest);
        User savedUser = userRepository.save(user);
        return userMapper.mapUserEntityToUserResponse(savedUser);
    }

    private boolean isUsernameExistInDatabase(String username) {
        return userRepository.existsUserByUsername(username);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long userId, UserRequest userRequest) throws ElementNotFoundException {
        return userRepository.findById(userId)
                .map(fetchedUser -> userMapper.mapUserRequestToUserEntity(userRequest, fetchedUser))
                .map(userMapper::mapUserEntityToUserResponse)
                .orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public void permanentDeleteUser(Long userId) throws ElementNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(ElementNotFoundException::new);
        userRepository.delete(user);
    }
}
