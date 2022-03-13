package wawer.kamil.beerproject.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.dto.request.BeerRequest;
import wawer.kamil.beerproject.dto.response.BeerResponse;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.service.BeerService;
import wawer.kamil.beerproject.utils.mapper.BeerMapper;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RestControllerAdvice
@Slf4j(topic = "application.logger")
public class BeerController {

    private final BeerService service;
    private final BeerMapper beerMapper;

    //get methods

    @GetMapping("beers")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<Page<BeerResponse>> findAllBeersPage(Pageable pageable) {
        Page<Beer> resultListOfBeers = service.findAllBeersPage(pageable);
        Page<BeerResponse> resultListOfBeersResponse = beerMapper.mapBeerEntityPageToBeerResponsePage(resultListOfBeers);
        log.debug("List of returned Id: {}", resultListOfBeers.stream().map(Beer::getBeerId).collect(Collectors.toList()));
        return ok().body(resultListOfBeersResponse);
    }

    @GetMapping("beers/list")
    public ResponseEntity<List<BeerResponse>> findAllBeersList() {
        List<Beer> resultListOfBeers = service.findAllBeersList();
        List<BeerResponse> resultListOfBeersDTO = beerMapper.mapListOfBeerEntityToListBeerResponse(resultListOfBeers);
        log.debug("List of returned Id: {}", resultListOfBeers.stream().map(Beer::getBeerId).collect(Collectors.toList()));
        return ok().body(resultListOfBeersDTO);
    }

    @GetMapping("brewery/{breweryId}/beers")
    public ResponseEntity<Page<BeerResponse>> findAllBeersByBreweryIdPage(@PathVariable Long breweryId, Pageable pageable) throws ElementNotFoundException {
        Page<Beer> resultListOfBeers = service.findAllBeersByBreweryIdPage(breweryId, pageable);
        Page<BeerResponse> resultListOfBeersDTO = beerMapper.mapBeerEntityPageToBeerResponsePage(resultListOfBeers);
        log.debug("List of returned beerId: {}", resultListOfBeers.stream().map(Beer::getBeerId).collect(Collectors.toList()));
        return ok().body(resultListOfBeersDTO);
    }

    @GetMapping("brewery/{breweryId}/beers/list")
    public ResponseEntity<List<BeerResponse>> findAllBeersByBreweryIdList(@PathVariable Long breweryId) throws ElementNotFoundException {
        List<Beer> resultListOfBeers = service.findAllBeersByBreweryIdList(breweryId);
        List<BeerResponse> resultListOfBeersDTO = beerMapper.mapListOfBeerEntityToListBeerResponse(resultListOfBeers);
        log.debug("List of returned beerId: {}", resultListOfBeers.stream().map(Beer::getBeerId).collect(Collectors.toList()));
        return ok().body(resultListOfBeersDTO);
    }

    @GetMapping("beers/{id}")
    public ResponseEntity<BeerResponse> findProperBeerByBeerId(@PathVariable Long id) throws ElementNotFoundException {
        Beer resultBeer = service.findBeerById(id);
        BeerResponse beerResponse = beerMapper.mapBeerToBeerResponse(resultBeer);
        return ok().body(beerResponse);
    }

    //post methods

    @PostMapping("brewery/{breweryId}/beers")
    public ResponseEntity<BeerResponse> addBeerToBreweryByBreweryId(@PathVariable Long breweryId, @Valid @RequestBody BeerRequest beerRequest) throws ElementNotFoundException, URISyntaxException {
        Beer mappedRequestBeerToBeerEntity = beerMapper.mapBeerRequestToBeerEntity(beerRequest);
        Beer savedBeer = service.addNewBeerAssignedToBreweryByBreweryId(breweryId, mappedRequestBeerToBeerEntity);
        BeerResponse beerResponse = beerMapper.mapBeerToBeerResponse(savedBeer);
        log.debug("Add new beer with Id: {}", beerResponse.getId());
        return created(new URI("add-beer" + savedBeer.getBeerId())).body(beerResponse);
    }

    //put methods

    @PutMapping("/beers/{id}")
    public ResponseEntity<BeerResponse> updateBeerBeerId(@PathVariable Long id,
                                                         @Valid @RequestBody BeerRequest beerRequest) throws ElementNotFoundException {
        Beer mappedBeer = beerMapper.mapBeerRequestToBeerEntity(beerRequest);
        Beer beer = service.updateBeerByBeerId(id, mappedBeer);
        BeerResponse beerResponse = beerMapper.mapBeerToBeerResponse(beer);
        return ok(beerResponse);
    }


    @PutMapping("brewery/{breweryId}/beers/{beerId}")
    public ResponseEntity<BeerResponse> updateBeerBaseOnBreweryIdAndBeerId(@PathVariable Long breweryId,
                                                                           @PathVariable Long beerId,
                                                                           @Valid @RequestBody BeerRequest beerRequest) throws ElementNotFoundException {
        Beer mappedBeer = beerMapper.mapBeerRequestToBeerEntity(beerRequest);
        Beer resultBeer = service.updateBeerByBreweryIdAndBeerId(breweryId, beerId, mappedBeer);
        BeerResponse response = beerMapper.mapBeerToBeerResponse(resultBeer);
        log.debug("Updated beer with Id: {}", resultBeer.getBeerId());
        return ok().body(response);
    }

    //delete methods

    @DeleteMapping("beers/{id}")
    public ResponseEntity deleteBeerByBeerId(@PathVariable Long id) throws ElementNotFoundException {
        service.deleteBeerById(id);
        return noContent().build();
    }

    @PostMapping(value = "beers/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImage(@PathVariable Long id, @RequestParam(name = "image") MultipartFile file) throws IOException, ElementNotFoundException, InvalidImageParameters {
        service.setBeerImageToBeerByBeerId(id, file);
        return ok().body("File is uploaded successfully");
    }

    @GetMapping(value = "beers/{id}/image", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long id) throws ElementNotFoundException {
        byte[] image = service.getBeerImageBaseOnBeerId(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return ok().headers(headers).body(image);
    }
}
