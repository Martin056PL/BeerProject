package wawer.kamil.beerproject.service.impl;

import lombok.RequiredArgsConstructor;
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
import wawer.kamil.beerproject.service.BreweryService;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static wawer.kamil.beerproject.service.impl.BreweryServiceHelper.getBreweriesWithBeers;
import static wawer.kamil.beerproject.utils.upload.ImageUpload.convertImageFileToByteArray;
import static wawer.kamil.beerproject.utils.upload.ImageUpload.validateFile;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "application.logger")
public class BreweryServiceImpl implements BreweryService {

    private final BreweryRepository breweryRepository;
    private final BeerRepository beerRepository;

    @Override
    @Transactional
    public Page<Brewery> getAllBreweryPage(Pageable pageable) {
        Page<Brewery> brewery = breweryRepository.findAll(pageable);
        List<Beer> beers = beerRepository.findBeersByListOfBreweriesId(getBreweriesIds(brewery));
        return getBreweriesWithBeers(brewery, beers);
    }

    @Override
    @Transactional
    public List<Brewery> getAllBreweryList() {
        List<Brewery> breweries = breweryRepository.findAll();
        List<Beer> beersByListOfBreweriesId = beerRepository.findBeersByListOfBreweriesId(getBreweriesIds(breweries));
        return getBreweriesWithBeers(breweries, beersByListOfBreweriesId);
    }

    private List<Long> getBreweriesIds(Page<Brewery> breweries) {
        return breweries.map(Brewery::getBreweryId).toList();
    }

    private List<Long> getBreweriesIds(List<Brewery> breweries) {
        return breweries.stream().map(Brewery::getBreweryId).collect(toList());
    }

    @Override
    public Brewery getBreweryById(Long id) throws ElementNotFoundException {
        return breweryRepository.findById(id).orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public Brewery createNewBrewery(Brewery brewery) {
        return breweryRepository.save(brewery);
    }

    @Override
    @Transactional
    public Brewery updateBreweryById(Long id, Brewery brewery) throws ElementNotFoundException {
        Brewery fetchedBrewery = breweryRepository.findById(id).orElseThrow(ElementNotFoundException::new);
        fetchedBrewery.setName(brewery.getName());
        fetchedBrewery.setEmail(brewery.getEmail());
        fetchedBrewery.setPhoneNumber(brewery.getPhoneNumber());
        fetchedBrewery.setAddress(brewery.getAddress());
        fetchedBrewery.setWebsite(brewery.getWebsite());
        fetchedBrewery.setBeerList(brewery.getBeerList());
        return fetchedBrewery;
    }

    @Override
    @Transactional
    public void deleteBreweryById(Long id) throws ElementNotFoundException {
        Brewery fetchedBrewery = breweryRepository.findById(id).orElseThrow(ElementNotFoundException::new);
        breweryRepository.delete(fetchedBrewery);
    }

    @Override
    @Transactional
    public void setBreweryImageToProperBreweryBaseOnBreweryId(Long breweryId, MultipartFile file) throws IOException, ElementNotFoundException, InvalidImageParameters {
        Brewery brewery = getBreweryById(breweryId);
        if (validateFile(file)) {
            byte[] imageAsByteArray = convertImageFileToByteArray(file);
            brewery.setBreweryImage(imageAsByteArray);
        } else {
            throw new InvalidImageParameters();
        }
    }

    @Override
    public byte[] getBreweryImageFromDbBaseOnBreweryId(Long breweryId) throws ElementNotFoundException {
        Brewery brewery = getBreweryById(breweryId);
        return brewery.getBreweryImage();
    }
}