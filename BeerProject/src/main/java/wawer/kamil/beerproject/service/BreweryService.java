package wawer.kamil.beerproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wawer.kamil.beerproject.domain.Brewery;
import wawer.kamil.beerproject.exceptions.NoContentException;

public interface BreweryService {

    Page<Brewery> getAllBrewery(Pageable pageable) throws NoContentException;

    Brewery getBreweryByBreweryId(Long breweryId) throws NoContentException;

    Brewery createNewBrewery(Brewery brewery);

    Brewery updateBreweryById(Long breweryId, Brewery brewery) throws NoContentException;

    void deleteBreweryByBreweryId(Long breweryId) throws NoContentException;
}
