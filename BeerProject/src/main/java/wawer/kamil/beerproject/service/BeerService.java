package wawer.kamil.beerproject.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.exceptions.NoContentException;

import java.io.IOException;
import java.util.List;

public interface BeerService {

    @Cacheable(value = "beerCache")
    Page<Beer> findAllBeersPage(Pageable pageable);

    @Cacheable(value = "beerCache")
    List<Beer> findAllBeersList();

    @Cacheable(value = "beerCache")
    Beer findBeerByBeerId(Long beerId) throws NoContentException;

    Page<Beer> findAllBeersByBreweryIdPage(Long breweryId, Pageable pageable) throws NoContentException;

    @CachePut(value = "beerCache", key = "#result.beerId")
    Beer addNewBeerToRepository(Beer beer);

    Beer addNewBeerAssignedToBreweryByBreweryId(Long breweryID, Beer beer) throws NoContentException;

    Beer updateBeerByBeerId(Long beerId, Beer beer) throws NoContentException;

    Beer updateBeerByBreweryIdAndBeerId(Long breweryId, Long beerId, Beer updatedBeer) throws NoContentException;

    @CacheEvict(value = "beerCache", beforeInvocation = false)
    void deleteBeerByBeerId(Long beerId) throws NoContentException;

    List<Beer> findAllBeersByBreweryIdList(Long breweryId) throws NoContentException;

    Beer findProperBeerByBreweryIdAndBeerId(Long breweryId, Long beerId) throws NoContentException;

    void deleteBeerByBreweryIdAndBeerId(Long breweryId, Long beerId) throws NoContentException;

    @Transactional
    void setBeerImageToProperBeerBaseOnBeerId(Long breweryId, Long beerId, MultipartFile file) throws IOException, NoContentException, InvalidImageParameters;

    byte[] getBeerImageFromDbBaseOnBreweryIdAndBeerId(Long breweryID, Long beerId) throws NoContentException;
}
