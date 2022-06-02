package wawer.kamil.beerproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wawer.kamil.beerproject.model.UserRegistrationData;

@Repository
public interface UserRegistrationDataRepository extends JpaRepository<UserRegistrationData, Long> {
}
