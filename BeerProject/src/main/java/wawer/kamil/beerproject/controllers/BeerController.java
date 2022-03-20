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
import wawer.kamil.beerproject.service.BeerService;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RestControllerAdvice
@Slf4j(topic = "application.logger")
public class BeerController {

    private final BeerService service;

    //get methods

    @GetMapping("beers/page")
    @PreAuthorize("hasAnyRole('USER','ADMIN','EXHIBITOR')")
    public ResponseEntity<Page<BeerResponse>> findAllBeersPage(Pageable pageable) {
        Page<BeerResponse> beersPage = service.findAllBeersPage(pageable);
        return ok().body(beersPage);
    }

    @GetMapping("beers/list")
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'EXHIBITOR')")
    public ResponseEntity<List<BeerResponse>> findAllBeersList() {
        List<BeerResponse> listOfBeers = service.findAllBeersList();
        return ok().body(listOfBeers);
    }

    @GetMapping("breweries/{breweryId}/beers/page")
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'EXHIBITOR')")
    public ResponseEntity<Page<BeerResponse>> findAllBeersByBreweryIdPage(@PathVariable Long breweryId, Pageable pageable) throws ElementNotFoundException {
        Page<BeerResponse> listOfBeers = service.findAllBeersByBreweryIdPage(breweryId, pageable);
        return ok().body(listOfBeers);
    }

    @GetMapping("breweries/{breweryId}/beers/list")
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'EXHIBITOR')")
    public ResponseEntity<List<BeerResponse>> findAllBeersByBreweryIdList(@PathVariable Long breweryId) throws ElementNotFoundException {
        List<BeerResponse> resultListOfBeers = service.findAllBeersByBreweryIdList(breweryId);
        return ok().body(resultListOfBeers);
    }

    @GetMapping("beers")
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'EXHIBITOR')")
    public ResponseEntity<BeerResponse> findProperBeerByBeerId(@RequestParam Long beerId) throws ElementNotFoundException {
        BeerResponse resultBeer = service.findBeerById(beerId);
        return ok().body(resultBeer);
    }

    //post methods

    @PostMapping("breweries/{breweryId}/beers")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXHIBITOR')")
    public ResponseEntity<BeerResponse> addBeerToBreweryByBreweryId(@PathVariable Long breweryId, @Valid @RequestBody BeerRequest beerRequest) throws ElementNotFoundException, URISyntaxException {
        BeerResponse savedBeer = service.addNewBeerAssignedToBreweryByBreweryId(breweryId, beerRequest);
        return created(new URI("add-beer" + savedBeer.getId())).body(savedBeer);
    }

    //put methods

    @PutMapping("/beers")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXHIBITOR')")
    public ResponseEntity<BeerResponse> updateBeerBeerId(@RequestParam Long beerId,
                                                         @Valid @RequestBody BeerRequest beerRequest) throws ElementNotFoundException {
        BeerResponse beerResponse = service.updateBeerByBeerId(beerId, beerRequest);
        return ok(beerResponse);
    }


    @PutMapping("breweries/{breweryId}/beers/{beerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXHIBITOR')")
    public ResponseEntity<BeerResponse> updateBeerBaseOnBreweryIdAndBeerId(@PathVariable Long breweryId,
                                                                           @PathVariable Long beerId,
                                                                           @Valid @RequestBody BeerRequest beerRequest) throws ElementNotFoundException {
        BeerResponse resultBeer = service.updateBeerByBreweryIdAndBeerId(breweryId, beerId, beerRequest);
        return ok().body(resultBeer);
    }

    //delete methods

    @DeleteMapping("beers")
    @PreAuthorize("hasAnyRole('ADMIN', 'EXHIBITOR')")
    public ResponseEntity<Object> deleteBeerByBeerId(@RequestParam Long beerId) throws ElementNotFoundException {
        service.deleteBeerById(beerId);
        return noContent().build();
    }

    @PostMapping(value = "beers/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'EXHIBITOR')")
    public ResponseEntity<Object> uploadImage(@RequestParam Long beerId, @RequestParam(name = "image") MultipartFile file) throws IOException, ElementNotFoundException, InvalidImageParameters {
        service.setBeerImageToBeerByBeerId(beerId, file);
        return ok().body("File is uploaded successfully");
    }

    @GetMapping(value = "beers/image", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'EXHIBITOR')")
    public ResponseEntity<byte[]> downloadImage(@RequestParam Long beerId) throws ElementNotFoundException {
        byte[] image = service.getBeerImageBaseOnBeerId(beerId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return ok().headers(headers).body(image);
    }
}
