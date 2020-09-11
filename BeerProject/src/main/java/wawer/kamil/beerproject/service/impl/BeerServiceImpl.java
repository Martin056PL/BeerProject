package wawer.kamil.beerproject.service.impl;

import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.model.Brewery;
import wawer.kamil.beerproject.repositories.BeerRepository;
import wawer.kamil.beerproject.repositories.BreweryRepository;
import wawer.kamil.beerproject.service.BeerService;
import wawer.kamil.beerproject.utils.upload.ImageUpload;

@Service
@AllArgsConstructor
@Slf4j(topic = "application.logger")
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BreweryRepository breweryRepository;
    private final ImageUpload imageUpload;

    private static final String THE_BREWERY_BASE_ON_ID_HAS_NOT_BEEN_FOUND = "The brewery base on id: {} has not been found";
    private static final String THE_BEER_BASE_ON_ID_HAS_NOT_BEEN_FOUND = "The beer base on id: {} has not been found";

    //get beers

    @Override
    @Cacheable(value = "beerCache")
    public Page<Beer> findAllBeersPage(Pageable pageable) {
        return beerRepository.findAll(pageable);
    }

    @Override
    @Cacheable(value = "beerCache")
    public List<Beer> findAllBeersList() {
        return beerRepository.findAll();
    }

    @Override
    @Cacheable(value = "beerCache")
    public Beer findBeerByBeerId(Long beerId) throws NoContentException {
        if (beerRepository.existsBeerByBeerId(beerId)) {
            return beerRepository.findBeerByBeerId(beerId);
        } else {
            log.debug(THE_BEER_BASE_ON_ID_HAS_NOT_BEEN_FOUND, beerId);
            throw new NoContentException();
        }
    }

    //get beers by breweryID

    @Override
    public Page<Beer> findAllBeersByBreweryIdPage(Long breweryId, Pageable pageable) throws NoContentException {
        if (breweryRepository.existsBreweryByBreweryId(breweryId)) {
            Brewery brewery = breweryRepository.findByBreweryId(breweryId);
            return beerRepository.findAllByBrewery(brewery, pageable);
        } else {
            log.debug(THE_BREWERY_BASE_ON_ID_HAS_NOT_BEEN_FOUND, breweryId);
            throw new NoContentException();
        }
    }

    @Override
    @Cacheable(value = "breweryCache", key = "#result.breweriId")
    public List<Beer> findAllBeersByBreweryIdList(Long breweryId) throws NoContentException {
        if (breweryRepository.existsBreweryByBreweryId(breweryId)) {
            Brewery brewery = breweryRepository.findByBreweryId(breweryId);
            return beerRepository.findAllByBrewery(brewery);
        } else {
            log.debug(THE_BREWERY_BASE_ON_ID_HAS_NOT_BEEN_FOUND, breweryId);
            throw new NoContentException();
        }
    }

    @Override
    @Caching( cacheable = {
            @Cacheable(value = "beerCache", key="#result.beerId"),
            @Cacheable(value = "breweryCache", key = "#result.breweriId")})
    public Beer findProperBeerByBreweryIdAndBeerId(Long breweryId, Long beerId) throws NoContentException {
        if (breweryRepository.existsBreweryByBreweryId(breweryId)) {
            if (beerRepository.existsBeerByBeerId(beerId)) {
                Brewery brewery = breweryRepository.findByBreweryId(breweryId);
                return beerRepository.findBeerByBreweryAndBeerId(brewery, beerId);
            } else {
                log.debug(THE_BEER_BASE_ON_ID_HAS_NOT_BEEN_FOUND, beerId);
                throw new NoContentException();
            }
        } else {
            log.debug(THE_BREWERY_BASE_ON_ID_HAS_NOT_BEEN_FOUND, breweryId);
            throw new NoContentException();
        }
    }

    //post beers

    @Override
    @CachePut(value = "beerCache", key = "#result.beerId")
    public Beer addNewBeerToRepository(Beer beer) {
        return beerRepository.save(beer);
    }

    @Override
    @Caching(put= {@CachePut(value = "beerCache", key = "#result.beerId")},
            cacheable = { @Cacheable(value = "breweryCache", key = "#result.breweryID")})
    public Beer addNewBeerAssignedToBreweryByBreweryId(Long breweryID, Beer beer) throws NoContentException {
        return breweryRepository.findById(breweryID)
                .map(brewery -> {
                    beer.setBrewery(brewery);
                    return beerRepository.save(beer);
                }).orElseThrow(NoContentException::new);
    }

    //put beers

    @Override
    @CachePut(value = "beerCache", key = "#result.beerId")
    public Beer updateBeerByBeerId(Long beerId, Beer beer) throws NoContentException {
        if (beerRepository.existsBeerByBeerId(beerId)) {
            beer.setBeerId(beerId);
            return beerRepository.save(beer);
        } else {
            log.debug(THE_BEER_BASE_ON_ID_HAS_NOT_BEEN_FOUND, beerId);
            throw new NoContentException();
        }
    }

    @Caching(put = {@CachePut(value = "breweryCache", key = "#result.breweryId"),
            @CachePut(value = "beerCache", key = "#result.beerId")})
    public Beer updateBeerByBreweryIdAndBeerId(Long breweryId, Long beerId, Beer updatedBeer) throws NoContentException {
        if (breweryRepository.existsBreweryByBreweryId(breweryId)) {
            if (beerRepository.existsBeerByBeerId(beerId)) {
                Beer beer = beerRepository.findBeerByBeerId(beerId);
                beer.setName(updatedBeer.getName());
                beer.setAlcohol(updatedBeer.getAlcohol());
                beer.setStyle(updatedBeer.getStyle());
                beer.setExtract(updatedBeer.getExtract());
                beer.setBeerImage(updatedBeer.getBeerImage());
                return beerRepository.save(beer);
            } else {
                log.debug(THE_BEER_BASE_ON_ID_HAS_NOT_BEEN_FOUND, beerId);
                throw new NoContentException();
            }
        } else {
            log.debug(THE_BREWERY_BASE_ON_ID_HAS_NOT_BEEN_FOUND, breweryId);
            throw new NoContentException();
        }
    }

    //delete beers

    @Override
    @CacheEvict(value = "beerCache", allEntries = true)
    public void deleteBeerByBeerId(Long beerId) throws NoContentException {
        if (beerRepository.existsBeerByBeerId(beerId)) {
            beerRepository.deleteById(beerId);
        } else {
            log.debug(THE_BEER_BASE_ON_ID_HAS_NOT_BEEN_FOUND, beerId);
            throw new NoContentException();
        }
    }

    @Override
    @Caching( evict = {
            @CacheEvict(value = "beerCache", key="#result.beerId", allEntries = true),
            @CacheEvict(value = "breweryCache", key = "#result.breweriId", allEntries = true)})
    public void deleteBeerByBreweryIdAndBeerId(Long breweryId, Long beerId) throws NoContentException {
        if (breweryRepository.existsBreweryByBreweryId(breweryId) && beerRepository.existsBeerByBeerId(beerId)) {
            beerRepository.deleteById(beerId);
        } else {
            log.debug("The brewery base on breweryId: {} or beer base on beerId: {} has not been found", breweryId, beerId);
            throw new NoContentException();
        }
    }

    @Override
    @Transactional
    public void setBeerImageToProperBeerBaseOnBeerId(Long breweryId, Long beerId, MultipartFile file) throws IOException, NoContentException, InvalidImageParameters {
        Beer beer = findProperBeerByBreweryIdAndBeerId(breweryId, beerId);
        if (imageUpload.validateSizeAndTypeOfFile(file)) {
            byte[] imageAsByteArray = imageUpload.convertFileToByteArray(file);
            beer.setBeerImage(imageAsByteArray);
            beerRepository.save(beer);
        } else {
            throw new InvalidImageParameters();
        }
    }

    @Override
    @Caching( cacheable = {
            @Cacheable(value = "beerCache", key="#result.beerId"),
            @Cacheable(value = "breweryCache", key = "#result.breweriId")})
    public byte[] getBeerImageFromDbBaseOnBreweryIdAndBeerId(Long breweryId, Long beerId) throws NoContentException {
        Beer beer = findProperBeerByBreweryIdAndBeerId(breweryId, beerId);
        if (beer.getBeerImage() == null) {
            throw new NoContentException();
        } else {
            return beer.getBeerImage();
        }
    }
}
