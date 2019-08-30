package wawer.kamil.beerproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wawer.kamil.beerproject.domain.User;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "application.logger")
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private static final String THE_USER_BASE_ON_ID_HAS_NOT_BEEN_FOUND = "The user base on id: {} has not been found";

    @Override
    public Page<User> findAllUsersPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public List<User> findAllUsersList() {
        return repository.findAll();
    }

    @Override
    public User findUserByUserId(Long userId) throws NoContentException {
        if (repository.existsByUserId(userId)) {
            return repository.findByUserId(userId);
        } else {
            log.debug(THE_USER_BASE_ON_ID_HAS_NOT_BEEN_FOUND, userId);
            throw new NoContentException();
        }
    }

    @Override
    public User createNewUser(User user) {
        return repository.save(user);
    }

    @Override
    public User updateUser(Long userId, User user) throws NoContentException {
        if (repository.existsByUserId(userId)) {
            user.setUserId(userId);
            return repository.save(user);
        } else {
            log.debug(THE_USER_BASE_ON_ID_HAS_NOT_BEEN_FOUND, userId);
            throw new NoContentException();
        }
    }

    @Override
    public void deleteUser(Long userId) throws NoContentException {
        if (repository.existsByUserId(userId)) {
            repository.deleteById(userId);
        } else {
            log.debug(THE_USER_BASE_ON_ID_HAS_NOT_BEEN_FOUND, userId);
            throw new NoContentException();
        }
    }


}
