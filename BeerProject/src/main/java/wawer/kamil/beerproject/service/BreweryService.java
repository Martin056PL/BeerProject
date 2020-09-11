package wawer.kamil.beerproject.service;

import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.model.Brewery;

public interface BreweryService {

    Page<Brewery> getAllBreweryPage(Pageable pageable) throws NoContentException;

    List<Brewery> getAllBreweryList();

    Brewery getBreweryByBreweryId(Long breweryId) throws NoContentException;

    Brewery createNewBrewery(Brewery brewery);

    Brewery updateBreweryById(Long breweryId, Brewery brewery) throws NoContentException;

    void deleteBreweryByBreweryId(Long breweryId) throws NoContentException;

    @Transactional
    void setBreweryImageToProperBreweryBaseOnBreweryId(Long breweryId, MultipartFile file) throws IOException, NoContentException, InvalidImageParameters;

    byte[] getBreweryImageFromDbBaseOnBreweryId(Long breweryId) throws NoContentException;
}
