package wawer.kamil.beerproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.model.Beer;

import java.io.IOException;
import java.util.List;

public interface BeerService {

    List<Beer> findBeerByListOfBreweriesId(List<Long> listOfBreweriesId);

    Page<Beer> findAllBeersPage(Pageable pageable);

    List<Beer> findAllBeersList();

    Beer findBeerByBeerId(Long beerId) throws NoContentException;

    Page<Beer> findAllBeersByBreweryIdPage(Long breweryId, Pageable pageable) throws NoContentException;

    Beer addNewBeerToRepository(Beer beer);

    Beer addNewBeerAssignedToBreweryByBreweryId(Long breweryID, Beer beer) throws NoContentException;

    Beer updateBeerByBeerId(Long beerId, Beer beer) throws NoContentException;

    Beer updateBeerByBreweryIdAndBeerId(Long breweryId, Long beerId, Beer updatedBeer) throws NoContentException;

    void deleteBeerByBeerId(Long beerId) throws NoContentException;

    List<Beer> findAllBeersByBreweryIdList(Long breweryId) throws NoContentException;

    Beer findProperBeerByBreweryIdAndBeerId(Long breweryId, Long beerId) throws NoContentException;

    void deleteBeerByBreweryIdAndBeerId(Long breweryId, Long beerId) throws NoContentException;

    @Transactional
    void setBeerImageToProperBeerBaseOnBeerId(Long breweryId, Long beerId, MultipartFile file) throws IOException, NoContentException, InvalidImageParameters;

    byte[] getBeerImageFromDbBaseOnBreweryIdAndBeerId(Long breweryID, Long beerId) throws NoContentException;
}
