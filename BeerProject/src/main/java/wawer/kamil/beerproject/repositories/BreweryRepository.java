package wawer.kamil.beerproject.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wawer.kamil.beerproject.model.Brewery;

import java.util.List;

public interface BreweryRepository extends JpaRepository<Brewery, Long> {

    @Query("select b from Brewery b left join fetch b.address")
    List<Brewery> findAllBreweries(Pageable pageable);

    Brewery findByBreweryId(Long breweryId);

    boolean existsBreweryByBreweryId(Long breweryId);

}
