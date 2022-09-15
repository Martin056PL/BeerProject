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
import wawer.kamil.beerproject.utils.mappers.EntityMapper;
import wawer.kamil.beerproject.utils.upload.ImageUpload;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static wawer.kamil.beerproject.service.impl.helper.BeerServiceHelper.mapBeerProperties;


@Service
@AllArgsConstructor
@Slf4j(topic = "application.logger")
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BreweryRepository breweryRepository;
    private final EntityMapper<Beer, BeerRequest, BeerResponse> mapper;
    private final ImageUpload imageUpload;

    //get beers

    @Override
    public Page<BeerResponse> findAllBeersPage(Pageable pageable) {
        List<BeerResponse> collectAllBeersPages = beerRepository.findAll(pageable).stream()
                .map(mapper::mapEntityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(collectAllBeersPages);
    }

    @Override
    public List<BeerResponse> findAllBeersList() {
        return beerRepository.findAll().stream()
                .map(mapper::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BeerResponse findBeerById(Long id) {
        return beerRepository.findById(id)
                .map(mapper::mapEntityToResponse)
                .orElseThrow(ElementNotFoundException::new);
    }

    @Override
    public Page<BeerResponse> findAllBeersByBreweryIdPage(Long breweryId, Pageable pageable) {
        Brewery fetchedBrewery = breweryRepository.findById(breweryId).orElseThrow(ElementNotFoundException::new);
        return beerRepository.findAllByBreweryId(fetchedBrewery.getBreweryId(), pageable)
                .map(mapper::mapEntityToResponse);
    }

    @Override
    public List<BeerResponse> findAllBeersByBreweryIdList(Long breweryId) {
        Brewery brewery = breweryRepository.findById(breweryId).orElseThrow(ElementNotFoundException::new);
        return beerRepository.findAllByBreweryId(brewery.getBreweryId())
                .stream()
                .map(mapper::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    //post beers

    @Override
    @Transactional
    public BeerResponse addNewBeerAssignedToBreweryByBreweryId(Long breweryID, BeerRequest beerRequest) {
        Beer requestedBeerEntity = mapper.mapRequestEntityToEntity(beerRequest);
        Brewery brewery = breweryRepository.findById(breweryID).orElseThrow(ElementNotFoundException::new);
        requestedBeerEntity.setBrewery(brewery);
        Beer savedBeer = beerRepository.save(requestedBeerEntity);
        return mapper.mapEntityToResponse(savedBeer);
    }

    //put beers

    @Override
    @Transactional
    public BeerResponse updateBeerByBeerId(Long beerId, BeerRequest updatedBeerRequest) {
        Beer mappedBeer = mapper.mapRequestEntityToEntity(updatedBeerRequest);
        Beer fetchedBeer = beerRepository.findById(beerId).orElseThrow(ElementNotFoundException::new);
        mapBeerProperties(fetchedBeer, mappedBeer);
        return mapper.mapEntityToResponse(fetchedBeer);
    }

    @Override
    @Transactional
    public BeerResponse updateBeerByBreweryIdAndBeerId(Long breweryId, Long beerId, BeerRequest updatedBeerRequest) {
        Beer mappedBeer = mapper.mapRequestEntityToEntity(updatedBeerRequest);
        Beer fetchedBeer = beerRepository.findBeerByBreweryAndBeerId(breweryId, beerId)
                .orElseThrow(ElementNotFoundException::new);
        mapBeerProperties(fetchedBeer, mappedBeer);
        return mapper.mapEntityToResponse(fetchedBeer);
    }

    //delete beers

    @Override
    @Transactional
    public void deleteBeerById(Long id) {
        Beer fetchedBeer = beerRepository.findById(id).orElseThrow(ElementNotFoundException::new);
        beerRepository.delete(fetchedBeer);
    }

    @Override
    @Transactional
    public void setBeerImageToBeerByBeerId(Long beerId, MultipartFile file) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(ElementNotFoundException::new);
        if (imageUpload.isFileValid(file)) {
            byte[] imageAsByteArray = imageUpload.convertImageToByteArray(file);
            beer.setBeerImage(imageAsByteArray);
        } else {
            throw new InvalidImageParameters();
        }
    }

    @Override
    public byte[] getBeerImageBaseOnBeerId(Long id) {
        return Optional.ofNullable(beerRepository.findBeerImageByBeerId(id)).orElseThrow(ElementNotFoundException::new);
    }
}
