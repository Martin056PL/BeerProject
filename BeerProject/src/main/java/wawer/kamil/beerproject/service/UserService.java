package wawer.kamil.beerproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import wawer.kamil.beerproject.configuration.security.ApplicationUserRole;
import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.model.user.User;

import java.util.List;

public interface UserService {

    UserDetails loadUserByUsername(String username);

    Page<UserResponse> findAllUsersPage(Pageable pageable);

    List<UserResponse> findAllUsersList();

    UserResponse getUserById(Long userId);

    User findUserById(Long userId);

    User getUserWithUserRegistrationData(UserRegistrationRequest request, ApplicationUserRole role);

    @Transactional
    User saveUser(User user);

    @Transactional
    UserResponse saveUser(UserRequest userRequest);

    UserResponse updateUser(Long userId, UserRequest userRequest);

    void enableUserAccount(User user);

    void permanentDeleteUser(Long userId);
}
