package wawer.kamil.beerproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wawer.kamil.beerproject.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsById(Long userId);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
