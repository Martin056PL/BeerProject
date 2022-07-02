package wawer.kamil.beerproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import wawer.kamil.beerproject.model.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select distinct u from User u join fetch u.grantedAuthorities where u.username =?1")
    Optional<User> findByUsername(String username);

    @Override
    @Query("select distinct u from User u join fetch u.grantedAuthorities")
    List<User> findAll();

    @Override
    @Query("select distinct u from User u join fetch u.grantedAuthorities join fetch u.userRegistrationData where u.id =?1")
    Optional<User> findById(Long id);

    boolean existsUserByUsername(String username);
}
