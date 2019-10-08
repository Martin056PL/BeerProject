package wawer.kamil.beerproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wawer.kamil.beerproject.model.Role;
import wawer.kamil.beerproject.model.enums.RoleName;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName roleName);

}
