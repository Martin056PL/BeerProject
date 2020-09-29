package wawer.kamil.beerproject.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.model.Brewery;

import java.util.List;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {

    @Query("select b from Beer b where b.brewery.breweryId in :breweryIdList")
    List<Beer> findBeerByListOfBreweriesId(@Param(value = "breweryIdList") List<Long> breweryIdList);

    Page<Beer> findAll(Pageable pageable);

    Page<Beer> findAllByBrewery(Brewery brewery, Pageable pageable);

    List<Beer> findAllByBrewery(Brewery brewery);

    Beer findBeerByBreweryAndBeerId(Brewery brewery, Long beerId);

    Beer findBeerByBeerId(Long beerId);

    boolean existsBeerByBeerId(Long beerId);

}
