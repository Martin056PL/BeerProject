package wawer.kamil.beerproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.exceptions.UsernameAlreadyExistsException;

import java.util.List;

public interface UserService {

    Page<UserResponse> findAllUsersPage(Pageable pageable);

    List<UserResponse> findAllUsersList();

    UserResponse findUserByUserId(Long userId) throws NoContentException;

    UserResponse addNewUser(UserRequest userRequest) throws UsernameAlreadyExistsException;
}
