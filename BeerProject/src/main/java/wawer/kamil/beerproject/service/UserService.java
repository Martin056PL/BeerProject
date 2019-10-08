package wawer.kamil.beerproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wawer.kamil.beerproject.model.User;
import wawer.kamil.beerproject.exceptions.NoContentException;

import java.util.List;

public interface UserService {

    Page<User> findAllUsersPage(Pageable pageable);

    List<User> findAllUsersList();

    User findUserByUserId(Long userId) throws NoContentException;

    User createNewUser(User user);

    User updateUser(Long userId, User user) throws NoContentException;

    void deleteUser(Long userId) throws NoContentException;
}
