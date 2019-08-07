package wawer.kamil.beerproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wawer.kamil.beerproject.domain.Beer;
import wawer.kamil.beerproject.exceptions.NoContentException;

public interface BeerService {

    Page<Beer> findAllBeersPage(Pageable pageable);

    Beer findBeerByBeerId(Long beerId) throws NoContentException;

    Beer addNewBeerToRepository(Beer beer);

    Beer updateBeerByBeerID(Long beerId, Beer beer) throws NoContentException;

    void deleteBeerByBeerId(Long beerId) throws NoContentException;
}
