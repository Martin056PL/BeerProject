package wawer.kamil.beerproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.dto.request.BeerRequest;
import wawer.kamil.beerproject.dto.response.BeerResponse;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.model.Beer;

import java.io.IOException;
import java.util.List;

public interface BeerService {

    Page<BeerResponse> findAllBeersPage(Pageable pageable);

    List<BeerResponse> findAllBeersList();

    BeerResponse findBeerById(Long beerId) throws ElementNotFoundException;

    Page<BeerResponse> findAllBeersByBreweryIdPage(Long breweryId, Pageable pageable) throws ElementNotFoundException;

    BeerResponse addNewBeerAssignedToBreweryByBreweryId(Long breweryID, BeerRequest beerRequest) throws ElementNotFoundException;

    BeerResponse updateBeerByBeerId(Long beerId, BeerRequest beerRequest) throws ElementNotFoundException;

    BeerResponse updateBeerByBreweryIdAndBeerId(Long breweryId, Long beerId, BeerRequest updatedBeerRequest) throws ElementNotFoundException;

    List<BeerResponse> findAllBeersByBreweryIdList(Long breweryId) throws ElementNotFoundException;

    void deleteBeerById(Long id) throws ElementNotFoundException;

    void setBeerImageToBeerByBeerId(Long id, MultipartFile file) throws IOException, ElementNotFoundException, InvalidImageParameters;

    byte[] getBeerImageBaseOnBeerId(Long id) throws ElementNotFoundException;
}
