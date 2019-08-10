package wawer.kamil.beerproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wawer.kamil.beerproject.domain.Beer;
import wawer.kamil.beerproject.domain.Brewery;
import wawer.kamil.beerproject.exceptions.NoContentException;

public interface BeerService {

    Page<Beer> findAllBeersPage(Pageable pageable);

    Beer findBeerByBeerId(Long beerId) throws NoContentException;

    Page<Beer> findAllBeersByBreweryId(Long breweryId, Pageable pageable) throws NoContentException;

    Beer addNewBeerToRepository(Beer beer);

    Brewery addNewBeerAssignedToBreweryByBreweryId(Long breweryID, Beer beer) throws NoContentException;

    Beer updateBeerByBeerID(Long beerId, Beer beer) throws NoContentException;

    void deleteBeerByBeerId(Long beerId) throws NoContentException;

    Beer findProperBeerByBreweryIdAndBeerId(Long breweryId, Long beerId) throws NoContentException;


}
