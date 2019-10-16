package wawer.kamil.beerproject.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wawer.kamil.beerproject.model.Brewery;

public interface BreweryRepository extends JpaRepository<Brewery, Long> {

    Page<Brewery> findAll(Pageable pageable);

    Brewery findByBreweryId(Long breweryId);

    boolean existsBreweryByBreweryId(Long breweryId);

}
