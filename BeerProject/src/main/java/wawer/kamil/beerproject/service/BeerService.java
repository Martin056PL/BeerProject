package wawer.kamil.beerproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wawer.kamil.beerproject.domain.Beer;
import wawer.kamil.beerproject.exceptions.NoContentException;

public interface BeerService {

    Page<Beer> findAllBeersPage(Pageable pageable);

    Beer findBeerByBeerId(Long beerId) throws NoContentException;

    Page<Beer> findAllBeersByBreweryId(Long breweryId, Pageable pageable) throws NoContentException;

    Beer addNewBeerToRepository(Beer beer);

    Beer addNewBeerAssignedToBreweryByBreweryId(Long breweryID, Beer beer) throws NoContentException;

    Beer updateBeerByBeerId(Long beerId, Beer beer) throws NoContentException;

    Beer updateBeerByBreweryIdAndBeerId(Long breweryId, Long beerId, Beer updatedBeer) throws NoContentException;

    void deleteBeerByBeerId(Long beerId) throws NoContentException;

    Beer findProperBeerByBreweryIdAndBeerId(Long breweryId, Long beerId) throws NoContentException;


}
