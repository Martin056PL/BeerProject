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
import java.util.Optional;

import static wawer.kamil.beerproject.service.impl.BeerServiceHelper.mapBeerProperties;


@Service
@AllArgsConstructor
@Slf4j(topic = "application.logger")
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BreweryRepository breweryRepository;
    private final ImageUpload imageUpload;

    //get beers

    @Override
    public Page<Beer> findAllBeersPage(Pageable pageable) {
        return beerRepository.findAll(pageable);
    }

    @Override
    public List<Beer> findAllBeersList() {
        return beerRepository.findAll();
    }

    @Override
    public Beer findBeerById(Long id) throws ElementNotFoundException {
        return beerRepository.findById(id).orElseThrow(ElementNotFoundException::new);
    }

    //get beers by breweryID

    @Override
    public Page<Beer> findAllBeersByBreweryIdPage(Long breweryId, Pageable pageable) throws ElementNotFoundException {
        Brewery fetchedBrewery = breweryRepository.findById(breweryId).orElseThrow(ElementNotFoundException::new);
        return beerRepository.findAllByBreweryId(fetchedBrewery.getBreweryId(), pageable);
    }

    @Override
    public List<Beer> findAllBeersByBreweryIdList(Long breweryId) throws ElementNotFoundException {
        Brewery brewery = breweryRepository.findById(breweryId).orElseThrow(ElementNotFoundException::new);
        return beerRepository.findAllByBreweryId(brewery.getBreweryId());
    }

    //post beers

    @Override
    @Transactional
    public Beer addNewBeerAssignedToBreweryByBreweryId(Long breweryID, Beer beer) throws ElementNotFoundException {
        Brewery brewery = breweryRepository.findById(breweryID).orElseThrow(ElementNotFoundException::new);
        beer.setBrewery(brewery);
        return beerRepository.save(beer);
    }

    //put beers

    @Override
    @Transactional
    public Beer updateBeerByBeerId(Long beerId, Beer updatedBeer) throws ElementNotFoundException {
        Beer fetchedBeer = beerRepository.findById(beerId).orElseThrow(ElementNotFoundException::new);
        mapBeerProperties(fetchedBeer, updatedBeer);
        return fetchedBeer;
    }

    @Override
    @Transactional
    public Beer updateBeerByBreweryIdAndBeerId(Long breweryId, Long beerId, Beer updatedBeer) throws ElementNotFoundException {
        Beer fetchedBeer = Optional.ofNullable(beerRepository.findBeerByBreweryAndBeerId(breweryId, beerId)).orElseThrow(ElementNotFoundException::new);
        mapBeerProperties(fetchedBeer, updatedBeer);
        return fetchedBeer;
    }

    //delete beers

    @Override
    public void deleteBeerById(Long id) throws ElementNotFoundException {
        try {
            beerRepository.deleteById(id);
        } catch (IllegalArgumentException ex) {
            log.debug("The beer base on id: {} has not been found", id);
            throw new ElementNotFoundException();
        }
    }

    @Override
    @Transactional
    public void setBeerImageToBeerByBeerId(Long beerId, MultipartFile file) throws IOException, ElementNotFoundException, InvalidImageParameters {
        Beer beer = findBeerById(beerId);
        if (imageUpload.validateFile(file)) {
            byte[] imageAsByteArray = imageUpload.convertImageToByteArray(file);
            beer.setBeerImage(imageAsByteArray);
        } else {
            throw new InvalidImageParameters();
        }
    }

    @Override
    public byte[] getBeerImageBaseOnBeerId(Long id) throws ElementNotFoundException {
        return Optional.ofNullable(beerRepository.findBeerImageByBeerId(id)).orElseThrow(ElementNotFoundException::new);
    }
}
