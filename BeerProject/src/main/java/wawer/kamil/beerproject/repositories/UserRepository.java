package wawer.kamil.beerproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wawer.kamil.beerproject.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserId(Long userId);

    User findByUserId(Long userId);
}
