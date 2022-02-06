package wawer.kamil.beerproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.model.Beer;

import java.io.IOException;
import java.util.List;

public interface BeerService {

    List<Beer> findBeerByListOfBreweriesId(List<Long> listOfBreweriesId);

    Page<Beer> findAllBeersPage(Pageable pageable);

    List<Beer> findAllBeersList();

    Beer findBeerByBeerId(Long beerId) throws ElementNotFoundException;

    Page<Beer> findAllBeersByBreweryIdPage(Long breweryId, Pageable pageable) throws ElementNotFoundException;

    Beer addNewBeerToRepository(Beer beer);

    Beer addNewBeerAssignedToBreweryByBreweryId(Long breweryID, Beer beer) throws ElementNotFoundException;

    Beer updateBeerByBeerId(Long beerId, Beer beer) throws ElementNotFoundException;

    Beer updateBeerByBreweryIdAndBeerId(Long breweryId, Long beerId, Beer updatedBeer) throws ElementNotFoundException;

    void deleteBeerByBeerId(Long beerId) throws ElementNotFoundException;

    List<Beer> findAllBeersByBreweryIdList(Long breweryId) throws ElementNotFoundException;

    Beer findProperBeerByBreweryIdAndBeerId(Long breweryId, Long beerId) throws ElementNotFoundException;

    void deleteBeerByBreweryIdAndBeerId(Long breweryId, Long beerId) throws ElementNotFoundException;

    @Transactional
    void setBeerImageToProperBeerBaseOnBeerId(Long breweryId, Long beerId, MultipartFile file) throws IOException, ElementNotFoundException, InvalidImageParameters;

    byte[] getBeerImageFromDbBaseOnBreweryIdAndBeerId(Long breweryID, Long beerId) throws ElementNotFoundException;
}
