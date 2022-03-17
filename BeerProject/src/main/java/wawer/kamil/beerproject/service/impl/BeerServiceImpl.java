package wawer.kamil.beerproject.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.dto.request.BeerRequest;
import wawer.kamil.beerproject.dto.response.BeerResponse;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.model.Brewery;
import wawer.kamil.beerproject.repositories.BeerRepository;
import wawer.kamil.beerproject.repositories.BreweryRepository;
import wawer.kamil.beerproject.service.BeerService;
import wawer.kamil.beerproject.utils.mapper.BeerMapper;
import wawer.kamil.beerproject.utils.upload.ImageUpload;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static wawer.kamil.beerproject.service.impl.BeerServiceHelper.mapBeerProperties;


@Service
@AllArgsConstructor
@Slf4j(topic = "application.logger")
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BreweryRepository breweryRepository;
    private final BeerMapper beerMapper;
    private final ImageUpload imageUpload;

    //get beers

    @Override
    public Page<BeerResponse> findAllBeersPage(Pageable pageable) {
        List<BeerResponse> collectAllBeersPages = beerRepository.findAll(pageable).stream()
                .map(beerMapper::mapBeerToBeerResponse)
                .collect(Collectors.toList());
        log.debug("List of returned Id: {}", collectAllBeersPages.stream().map(BeerResponse::getId).collect(Collectors.toList()));
        return new PageImpl<>(collectAllBeersPages);
    }

    @Override
    public List<BeerResponse> findAllBeersList() {
        return beerRepository.findAll().stream()
                .map(beerMapper::mapBeerToBeerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BeerResponse findBeerById(Long id) throws ElementNotFoundException {
        return beerRepository.findById(id)
                .map(beerMapper::mapBeerToBeerResponse)
                .orElseThrow(ElementNotFoundException::new);
    }

    //get beers by breweryID

    @Override
    public Page<BeerResponse> findAllBeersByBreweryIdPage(Long breweryId, Pageable pageable) throws ElementNotFoundException {
        Brewery fetchedBrewery = breweryRepository.findById(breweryId).orElseThrow(ElementNotFoundException::new);
        // TODO move to aspects -> log.debug("List of returned beerId: {}", listOfBeers.stream().map(BeerResponse::getId).collect(Collectors.toList()));
        return beerRepository.findAllByBreweryId(fetchedBrewery.getBreweryId(), pageable)
                .map(beerMapper::mapBeerToBeerResponse);
    }

    @Override
    public List<BeerResponse> findAllBeersByBreweryIdList(Long breweryId) throws ElementNotFoundException {
        Brewery brewery = breweryRepository.findById(breweryId).orElseThrow(ElementNotFoundException::new);
        // TODO move to aspects -> log.debug("List of returned beerId: {}", resultListOfBeers.stream().map(BeerResponse::getId).collect(Collectors.toList()));
        return beerRepository.findAllByBreweryId(brewery.getBreweryId())
                .stream()
                .map(beerMapper::mapBeerToBeerResponse)
                .collect(Collectors.toList());
    }

    //post beers

    @Override
    @Transactional
    public BeerResponse addNewBeerAssignedToBreweryByBreweryId(Long breweryID, BeerRequest beerRequest) throws ElementNotFoundException {
        Beer requestedBeerEntity = beerMapper.mapBeerRequestToBeerEntity(beerRequest);
        Brewery brewery = breweryRepository.findById(breweryID).orElseThrow(ElementNotFoundException::new);
        requestedBeerEntity.setBrewery(brewery);
        Beer savedBeer = beerRepository.save(requestedBeerEntity);
        return beerMapper.mapBeerToBeerResponse(savedBeer);

    }

    //put beers

    @Override
    @Transactional
    public BeerResponse updateBeerByBeerId(Long beerId, BeerRequest updatedBeerRequest) throws ElementNotFoundException {
        Beer mappedBeer = beerMapper.mapBeerRequestToBeerEntity(updatedBeerRequest);
        Beer fetchedBeer = beerRepository.findById(beerId).orElseThrow(ElementNotFoundException::new);
        mapBeerProperties(fetchedBeer, mappedBeer);
        return beerMapper.mapBeerToBeerResponse(fetchedBeer);
    }

    @Override
    @Transactional
    public BeerResponse updateBeerByBreweryIdAndBeerId(Long breweryId, Long beerId, BeerRequest updatedBeerRequest) throws ElementNotFoundException {
        Beer mappedBeer = beerMapper.mapBeerRequestToBeerEntity(updatedBeerRequest);
        Beer fetchedBeer = beerRepository.findBeerByBreweryAndBeerId(breweryId, beerId).orElseThrow(ElementNotFoundException::new);
        mapBeerProperties(fetchedBeer, mappedBeer);
        return beerMapper.mapBeerToBeerResponse(fetchedBeer);
    }

    //delete beers

    @Override
    @Transactional
    public void deleteBeerById(Long id) throws ElementNotFoundException {
        Beer fetchedBeer = beerRepository.findById(id).orElseThrow(ElementNotFoundException::new);
        beerRepository.delete(fetchedBeer);
    }

    @Override
    @Transactional
    public void setBeerImageToBeerByBeerId(Long beerId, MultipartFile file) throws IOException, ElementNotFoundException, InvalidImageParameters {
        Beer beer = beerRepository.findById(beerId).orElseThrow(ElementNotFoundException::new);
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
