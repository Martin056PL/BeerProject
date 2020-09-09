package wawer.kamil.beerproject.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.model.Brewery;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.exceptions.NoContentException;

import java.io.IOException;
import java.util.List;

public interface BreweryService {

    @Cacheable(value= "breweryCache")
    Page<Brewery> getAllBreweryPage(Pageable pageable) throws NoContentException;

    @Cacheable(value= "breweryCache")
    List<Brewery> getAllBreweryList();

    @Cacheable(value= "breweryCache")
    Brewery getBreweryByBreweryId(Long breweryId) throws NoContentException;

    @CachePut(value = "breweryCache", key = "#result.breweryId")
    Brewery createNewBrewery(Brewery brewery);

    Brewery updateBreweryById(Long breweryId, Brewery brewery) throws NoContentException;

    @CacheEvict(value = "breweryCache")
    void deleteBreweryByBreweryId(Long breweryId) throws NoContentException;

    @Transactional
    void setBreweryImageToProperBreweryBaseOnBreweryId(Long breweryId, MultipartFile file) throws IOException, NoContentException, InvalidImageParameters;

    byte[] getBreweryImageFromDbBaseOnBreweryId(Long breweryId) throws NoContentException;
}
