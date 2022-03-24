package wawer.kamil.beerproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.model.User;

import java.util.List;

public interface UserService {

    User generateDefaultUserToDatabase();

    Page<UserResponse> findAllUsersPage(Pageable pageable);

    List<UserResponse> findAllUsersList();

    UserResponse findUserByUserId(Long userId);

    UserResponse addNewUser(UserRequest userRequest);

    UserResponse updateUser(Long userId, UserRequest userRequest);

    void permanentDeleteUser(Long userId);
}
