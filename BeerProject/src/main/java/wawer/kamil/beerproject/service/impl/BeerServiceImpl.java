package wawer.kamil.beerproject.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.model.Brewery;
import wawer.kamil.beerproject.repositories.BeerRepository;
import wawer.kamil.beerproject.repositories.BreweryRepository;
import wawer.kamil.beerproject.service.BeerService;
import wawer.kamil.beerproject.utils.upload.ImageUpload;

import java.io.IOException;
import java.util.List;


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
    public List<Beer> findBeerByListOfBreweriesId(List<Long> listOfBreweriesId) {
        return beerRepository.findBeersByListOfBreweriesId(listOfBreweriesId);
    }

    @Override
    public Page<Beer> findAllBeersPage(Pageable pageable) {
        return beerRepository.findAll(pageable);
    }

    @Override
    public List<Beer> findAllBeersList() {
        return beerRepository.findAll();
    }

    @Override
    public Beer findBeerByBeerId(Long beerId) throws ElementNotFoundException {
        if (beerRepository.existsBeerByBeerId(beerId)) {
            return beerRepository.findBeerByBeerId(beerId);
        } else {
            log.debug(THE_BEER_BASE_ON_ID_HAS_NOT_BEEN_FOUND, beerId);
            throw new ElementNotFoundException();
        }
    }

    //get beers by breweryID

    @Override
    public Page<Beer> findAllBeersByBreweryIdPage(Long breweryId, Pageable pageable) throws ElementNotFoundException {
        Brewery brewery = breweryRepository.findById(breweryId)
                .orElseThrow(() -> {
                    log.debug(THE_BREWERY_BASE_ON_ID_HAS_NOT_BEEN_FOUND, breweryId);
                    return new ElementNotFoundException();
                });
        return beerRepository.findAllByBrewery(brewery, pageable);
    }

    @Override
    public List<Beer> findAllBeersByBreweryIdList(Long breweryId) throws ElementNotFoundException {
        Brewery brewery = breweryRepository.findById(breweryId)
                .orElseThrow(() -> {
                    log.debug(THE_BREWERY_BASE_ON_ID_HAS_NOT_BEEN_FOUND, breweryId);
                    return new ElementNotFoundException();
                });
        return beerRepository.findAllByBrewery(brewery);
    }

    @Override
    public Beer findProperBeerByBreweryIdAndBeerId(Long breweryId, Long beerId) throws ElementNotFoundException {
//        if (breweryRepository.existsBreweryByBreweryId(breweryId)) {
//            if (beerRepository.existsBeerByBeerId(beerId)) {
//                Brewery brewery = breweryRepository.findByBreweryId(breweryId);
//                return beerRepository.findBeerByBreweryAndBeerId(brewery, beerId);
//            } else {
//                log.debug(THE_BEER_BASE_ON_ID_HAS_NOT_BEEN_FOUND, beerId);
//                throw new ElementNotFoundException();
//            }
//        } else {
//            log.debug(THE_BREWERY_BASE_ON_ID_HAS_NOT_BEEN_FOUND, breweryId);
//            throw new ElementNotFoundException();
//        }
        return null;
    }

    //post beers

    @Override
    public Beer addNewBeerToRepository(Beer beer) {
        return beerRepository.save(beer);
    }

    @Override
    public Beer addNewBeerAssignedToBreweryByBreweryId(Long breweryID, Beer beer) throws ElementNotFoundException {
        return breweryRepository.findById(breweryID)
                .map(brewery -> {
                    beer.setBrewery(brewery);
                    return beerRepository.save(beer);
                }).orElseThrow(ElementNotFoundException::new);
    }

    //put beers

    @Override
    public Beer updateBeerByBeerId(Long beerId, Beer beer) throws ElementNotFoundException {
        if (beerRepository.existsBeerByBeerId(beerId)) {
            beer.setBeerId(beerId);
            return beerRepository.save(beer);
        } else {
            log.debug(THE_BEER_BASE_ON_ID_HAS_NOT_BEEN_FOUND, beerId);
            throw new ElementNotFoundException();
        }
    }

    public Beer updateBeerByBreweryIdAndBeerId(Long breweryId, Long beerId, Beer updatedBeer) throws ElementNotFoundException {
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
                throw new ElementNotFoundException();
            }
        } else {
            log.debug(THE_BREWERY_BASE_ON_ID_HAS_NOT_BEEN_FOUND, breweryId);
            throw new ElementNotFoundException();
        }
    }

    //delete beers

    @Override
    public void deleteBeerByBeerId(Long beerId) throws ElementNotFoundException {
        if (beerRepository.existsBeerByBeerId(beerId)) {
            beerRepository.deleteById(beerId);
        } else {
            log.debug(THE_BEER_BASE_ON_ID_HAS_NOT_BEEN_FOUND, beerId);
            throw new ElementNotFoundException();
        }
    }

    @Override
    public void deleteBeerByBreweryIdAndBeerId(Long breweryId, Long beerId) throws ElementNotFoundException {
        if (breweryRepository.existsBreweryByBreweryId(breweryId) && beerRepository.existsBeerByBeerId(beerId)) {
            beerRepository.deleteById(beerId);
        } else {
            log.debug("The brewery base on breweryId: {} or beer base on beerId: {} has not been found", breweryId, beerId);
            throw new ElementNotFoundException();
        }
    }

    @Override
    @Transactional
    public void setBeerImageToProperBeerBaseOnBeerId(Long breweryId, Long beerId, MultipartFile file) throws IOException, ElementNotFoundException, InvalidImageParameters {
        Beer beer = findProperBeerByBreweryIdAndBeerId(breweryId, beerId);
        if (imageUpload.validateFile(file)) {
            byte[] imageAsByteArray = imageUpload.convertImageFileToByteArray(file);
            beer.setBeerImage(imageAsByteArray);
            beerRepository.save(beer);
        } else {
            throw new InvalidImageParameters();
        }
    }

    @Override
    public byte[] getBeerImageFromDbBaseOnBreweryIdAndBeerId(Long breweryId, Long beerId) throws ElementNotFoundException {
        Beer beer = findProperBeerByBreweryIdAndBeerId(breweryId, beerId);
        if (beer.getBeerImage() == null) {
            throw new ElementNotFoundException();
        } else {
            return beer.getBeerImage();
        }
    }
}
