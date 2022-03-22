package wawer.kamil.beerproject.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.dto.request.BreweryRequest;
import wawer.kamil.beerproject.dto.response.BreweryResponse;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.model.Brewery;
import wawer.kamil.beerproject.repositories.BeerRepository;
import wawer.kamil.beerproject.repositories.BreweryRepository;
import wawer.kamil.beerproject.service.BreweryService;
import wawer.kamil.beerproject.utils.mapper.BreweryMapper;
import wawer.kamil.beerproject.utils.upload.ImageUpload;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static wawer.kamil.beerproject.service.impl.BreweryServiceHelper.*;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "application.logger")
public class BreweryServiceImpl implements BreweryService {

    private final BreweryRepository breweryRepository;
    private final BeerRepository beerRepository;
    private final BreweryMapper breweryMapper;
    private final ImageUpload imageUpload;

    @Override
    @Transactional
    public Page<BreweryResponse> getAllBreweryPage(Pageable pageable) {
        Page<Brewery> brewery = breweryRepository.findAll(pageable);
        List<Long> breweriesIds = getBreweriesIds(brewery);
        List<Beer> beers = beerRepository.findBeersByListOfBreweriesId(breweriesIds);
        Page<Brewery> breweriesWithBeers = getBreweriesWithBeers(brewery, beers);
        return breweryMapper.mapBreweryEntityPageToBreweryResponsePage(breweriesWithBeers);
    }

    @Override
    @Transactional
    public List<BreweryResponse> getAllBreweryList() {
        List<Brewery> breweries = breweryRepository.findAll();
        List<Beer> beersByListOfBreweriesId = beerRepository.findBeersByListOfBreweriesId(getBreweriesIds(breweries));
        List<Brewery> breweriesWithBeers = getBreweriesWithBeers(breweries, beersByListOfBreweriesId);
        return breweriesWithBeers.stream()
                .map(breweryMapper::mapBreweryToBreweryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BreweryResponse findBreweryById(Long id) throws ElementNotFoundException {
        return breweryRepository.findById(id)
                .map(breweryMapper::mapBreweryToBreweryResponse)
                .orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public BreweryResponse createNewBrewery(BreweryRequest breweryRequest) {
        Brewery brewery = breweryMapper.mapBreweryRequestToBreweryEntity(breweryRequest);
        brewery.assignBreweryToAllBeersOnBrewerysList();
        Brewery savedBrewery = breweryRepository.save(brewery);
        return breweryMapper.mapBreweryToBreweryResponse(savedBrewery);
    }

    @Override
    @Transactional
    public BreweryResponse updateBreweryById(Long id, BreweryRequest breweryRequest) throws ElementNotFoundException {
        Brewery mappedBrewery = breweryMapper.mapBreweryRequestToBreweryEntity(breweryRequest);
        Brewery fetchedBrewery = breweryRepository.findById(id).orElseThrow(ElementNotFoundException::new);
        mapBreweryProperties(fetchedBrewery, mappedBrewery);
        return breweryMapper.mapBreweryToBreweryResponse(fetchedBrewery);
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
        Brewery brewery = breweryRepository.findById(breweryId).orElseThrow(ElementNotFoundException::new);
        if (imageUpload.validateFile(file)) {
            byte[] imageAsByteArray = imageUpload.convertImageToByteArray(file);
            brewery.setBreweryImage(imageAsByteArray);
        } else {
            throw new InvalidImageParameters();
        }
    }

    @Override
    public byte[] getBreweryImageFromDbBaseOnBreweryId(Long breweryId) throws ElementNotFoundException {
        Brewery brewery = breweryRepository.findById(breweryId).orElseThrow(ElementNotFoundException::new);
        return brewery.getBreweryImage();
    }
}