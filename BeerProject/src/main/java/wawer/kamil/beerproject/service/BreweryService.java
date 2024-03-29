package wawer.kamil.beerproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.dto.request.BreweryRequest;
import wawer.kamil.beerproject.dto.response.BreweryResponse;

import java.util.List;

public interface BreweryService {

    Page<BreweryResponse> getAllBreweryPage(Pageable pageable);

    List<BreweryResponse> getAllBreweryList();

    BreweryResponse findBreweryById(Long id);

    BreweryResponse createNewBrewery(BreweryRequest breweryRequest);

    BreweryResponse updateBreweryById(Long breweryId, BreweryRequest brewery);

    void deleteBreweryById(Long breweryId);

    void setBreweryImageToProperBreweryBaseOnBreweryId(Long breweryId, MultipartFile file);

    byte[] getBreweryImageFromDbBaseOnBreweryId(Long breweryId);
}
