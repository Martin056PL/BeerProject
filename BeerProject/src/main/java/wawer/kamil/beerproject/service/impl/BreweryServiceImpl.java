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
import wawer.kamil.beerproject.utils.mappers.EntityMapper;
import wawer.kamil.beerproject.utils.upload.ImageUpload;

import java.util.List;
import java.util.stream.Collectors;

import static wawer.kamil.beerproject.service.impl.helper.BreweryServiceHelper.*;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "application.logger")
public class BreweryServiceImpl implements BreweryService {

    private final BreweryRepository breweryRepository;
    private final BeerRepository beerRepository;
    private final EntityMapper<Brewery, BreweryRequest, BreweryResponse> entityMapper;
    private final ImageUpload imageUpload;

    @Override
    @Transactional
    public Page<BreweryResponse> getAllBreweryPage(Pageable pageable) {
        Page<Brewery> brewery = breweryRepository.findAll(pageable);
        List<Long> breweriesIds = getBreweriesIds(brewery);
        List<Beer> beers = beerRepository.findBeersByListOfBreweriesId(breweriesIds);
        Page<Brewery> breweriesWithBeers = getBreweriesWithBeers(brewery, beers);
        return entityMapper.mapEntityPageToResponsePage(breweriesWithBeers);
    }

    @Override
    @Transactional
    public List<BreweryResponse> getAllBreweryList() {
        List<Brewery> breweries = breweryRepository.findAll();
        List<Beer> beersByListOfBreweriesId = beerRepository.findBeersByListOfBreweriesId(getBreweriesIds(breweries));
        List<Brewery> breweriesWithBeers = getBreweriesWithBeers(breweries, beersByListOfBreweriesId);
        return breweriesWithBeers.stream()
                .map(entityMapper::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BreweryResponse findBreweryById(Long id) {
        return breweryRepository.findById(id)
                .map(entityMapper::mapEntityToResponse)
                .orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public BreweryResponse createNewBrewery(BreweryRequest breweryRequest) {
        Brewery brewery = entityMapper.mapRequestEntityToEntity(breweryRequest);
        brewery.assignBreweryToAllBeersOnBreweriesList();
        brewery.assignAddressToBrewery();
        Brewery savedBrewery = breweryRepository.save(brewery);
        return entityMapper.mapEntityToResponse(savedBrewery);
    }

    @Override
    @Transactional
    public BreweryResponse updateBreweryById(Long id, BreweryRequest breweryRequest) {
        Brewery mappedBrewery = entityMapper.mapRequestEntityToEntity(breweryRequest);
        Brewery fetchedBrewery = breweryRepository.findById(id).orElseThrow(ElementNotFoundException::new);
        mapBreweryProperties(fetchedBrewery, mappedBrewery);
        return entityMapper.mapEntityToResponse(fetchedBrewery);
    }

    @Override
    @Transactional
    public void deleteBreweryById(Long id) {
        Brewery fetchedBrewery = breweryRepository.findById(id).orElseThrow(ElementNotFoundException::new);
        breweryRepository.delete(fetchedBrewery);
    }

    @Override
    @Transactional
    public void setBreweryImageToProperBreweryBaseOnBreweryId(Long breweryId, MultipartFile file) {
        Brewery brewery = breweryRepository.findById(breweryId).orElseThrow(ElementNotFoundException::new);
        if (!imageUpload.isFileValid(file)) {
            throw new InvalidImageParameters();
        }
        byte[] imageAsByteArray = imageUpload.convertImageToByteArray(file);
        brewery.setBreweryImage(imageAsByteArray);
    }

    @Override
    public byte[] getBreweryImageFromDbBaseOnBreweryId(Long breweryId) {
        Brewery brewery = breweryRepository.findById(breweryId).orElseThrow(ElementNotFoundException::new);
        return brewery.getBreweryImage();
    }
}