package wawer.kamil.beerproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.dto.request.BreweryRequest;
import wawer.kamil.beerproject.dto.response.BreweryResponse;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;

import java.io.IOException;
import java.util.List;

public interface BreweryService {

    Page<BreweryResponse> getAllBreweryPage(Pageable pageable);

    List<BreweryResponse> getAllBreweryList();

    BreweryResponse findBreweryById(Long id) ;

    BreweryResponse createNewBrewery(BreweryRequest breweryRequest);

    BreweryResponse updateBreweryById(Long breweryId, BreweryRequest brewery) ;

    void deleteBreweryById(Long breweryId) ;

    void setBreweryImageToProperBreweryBaseOnBreweryId(Long breweryId, MultipartFile file) throws IOException;

    byte[] getBreweryImageFromDbBaseOnBreweryId(Long breweryId) ;
}
