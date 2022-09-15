package wawer.kamil.beerproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wawer.kamil.beerproject.configuration.security.ApplicationUserRole;
import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.UsernameAlreadyExistsException;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.model.user.factory.UserFactory;
import wawer.kamil.beerproject.repositories.UserRepository;
import wawer.kamil.beerproject.service.UserService;
import wawer.kamil.beerproject.utils.mappers.EntityMapper;

import java.util.List;

@Service(value = "UserServiceImpl")
@AllArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final EntityMapper<User, UserRequest, UserResponse> userMapper;
    private static final String USER_NOT_FOUND = "User with email %s not found";

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, username)));
    }

    @Override
    public Page<UserResponse> findAllUsersPage(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return userMapper.mapEntityPageToResponsePage(userPage);
    }

    @Override
    public List<UserResponse> findAllUsersList() {
        List<User> userList = userRepository.findAll();
        return userMapper.mapEntitiesToEntitiesResponse(userList);
    }

    @Override
    public UserResponse getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::mapEntityToResponse)
                .orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public User getUserWithUserRegistrationData(UserRegistrationRequest request, ApplicationUserRole role) {
        validateIfUsernameIsAlreadyInUse(request);
        return userFactory.createNewUser(request, role);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public UserResponse saveUser(UserRequest userRequest) {
        if (isUsernameExistInDatabase(userRequest.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }
        User user = userMapper.mapRequestEntityToEntity(userRequest);
        User savedUser = userRepository.save(user);
        return userMapper.mapEntityToResponse(savedUser);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long userId, UserRequest userRequest) {
        return userRepository.findById(userId)
                .map(fetchedUser -> userMapper.mapRequestEntityToEntity(userRequest))
                .map(userMapper::mapEntityToResponse)
                .orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public void enableUserAccount(User user) {
        user.setEnabled(true);
        user.getUserRegistrationData().setConfirmed(true);
    }

    @Override
    public void permanentDeleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(ElementNotFoundException::new);
        userRepository.delete(user);
    }

    private boolean isUsernameExistInDatabase(String username) {
        return userRepository.existsUserByUsername(username);
    }

    private void validateIfUsernameIsAlreadyInUse(UserRegistrationRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent(user -> {
            throw new UsernameAlreadyExistsException();
        });
    }
}
