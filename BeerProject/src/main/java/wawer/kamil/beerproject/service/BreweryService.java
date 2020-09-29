package wawer.kamil.beerproject.service;

import wawer.kamil.beerproject.dto.request.PageParams;
import wawer.kamil.beerproject.dto.response.BreweryResponse;

import java.util.List;

public interface BreweryService {

    List<BreweryResponse> getAllBreweryPage(PageParams pageParams);
//
//    List<Brewery> getAllBreweryList();
//
//    Brewery getBreweryByBreweryId(Long breweryId) throws NoContentException;
//
//    Brewery createNewBrewery(Brewery brewery);
//
//    Brewery updateBreweryById(Long breweryId, Brewery brewery) throws NoContentException;
//
//    void deleteBreweryByBreweryId(Long breweryId) throws NoContentException;
//
//    @Transactional
//    void setBreweryImageToProperBreweryBaseOnBreweryId(Long breweryId, MultipartFile file) throws IOException, NoContentException, InvalidImageParameters;
//
//    byte[] getBreweryImageFromDbBaseOnBreweryId(Long breweryId) throws NoContentException;
}
