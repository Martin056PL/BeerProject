package wawer.kamil.beerproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.dto.request.BeerRequest;
import wawer.kamil.beerproject.dto.response.BeerResponse;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;

import java.io.IOException;
import java.util.List;

public interface BeerService {

    Page<BeerResponse> findAllBeersPage(Pageable pageable);

    List<BeerResponse> findAllBeersList();

    BeerResponse findBeerById(Long beerId) ;

    Page<BeerResponse> findAllBeersByBreweryIdPage(Long breweryId, Pageable pageable) ;

    BeerResponse addNewBeerAssignedToBreweryByBreweryId(Long breweryID, BeerRequest beerRequest) ;

    BeerResponse updateBeerByBeerId(Long beerId, BeerRequest beerRequest) ;

    BeerResponse updateBeerByBreweryIdAndBeerId(Long breweryId, Long beerId, BeerRequest updatedBeerRequest) ;

    List<BeerResponse> findAllBeersByBreweryIdList(Long breweryId) ;

    void deleteBeerById(Long id) ;

    void setBeerImageToBeerByBeerId(Long id, MultipartFile file) throws IOException;

    byte[] getBeerImageBaseOnBeerId(Long id) ;
}
