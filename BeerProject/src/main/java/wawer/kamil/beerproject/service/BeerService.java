package wawer.kamil.beerproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.model.Beer;

import java.io.IOException;
import java.util.List;

public interface BeerService {

    Page<Beer> findAllBeersPage(Pageable pageable);

    List<Beer> findAllBeersList();

    Beer findBeerById(Long beerId) throws ElementNotFoundException;

    Page<Beer> findAllBeersByBreweryIdPage(Long breweryId, Pageable pageable) throws ElementNotFoundException;

    Beer addNewBeerAssignedToBreweryByBreweryId(Long breweryID, Beer beer) throws ElementNotFoundException;

    Beer updateBeerByBeerId(Long beerId, Beer beer) throws ElementNotFoundException;

    Beer updateBeerByBreweryIdAndBeerId(Long breweryId, Long beerId, Beer updatedBeer) throws ElementNotFoundException;

    List<Beer> findAllBeersByBreweryIdList(Long breweryId) throws ElementNotFoundException;

    void deleteBeerById(Long id) throws ElementNotFoundException;

    void setBeerImageToBeerByBeerId(Long id, MultipartFile file) throws IOException, ElementNotFoundException, InvalidImageParameters;

    byte[] getBeerImageBaseOnBeerId(Long id) throws ElementNotFoundException;
}
