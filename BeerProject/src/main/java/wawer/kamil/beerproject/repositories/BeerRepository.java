package wawer.kamil.beerproject.repositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.model.Brewery;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {

    Page<Beer> findAll(Pageable pageable);

    Page<Beer> findAllByBrewery(Brewery brewery, Pageable pageable);

    List<Beer> findAllByBrewery(Brewery brewery);

    Beer findBeerByBreweryAndBeerId(Brewery brewery, Long beerId);

    Beer findBeerByBeerId(Long beerId);

    boolean existsBeerByBeerId(Long beerId);

}
