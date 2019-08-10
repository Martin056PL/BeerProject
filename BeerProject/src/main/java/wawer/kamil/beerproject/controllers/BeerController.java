package wawer.kamil.beerproject.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wawer.kamil.beerproject.domain.Beer;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.service.BeerService;

import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@CrossOrigin
@Controller
@RestControllerAdvice
public class BeerController {

    private final BeerService service;

    //get methods

    @GetMapping("beer")
    public ResponseEntity<Page<Beer>> findAllBeers(Pageable pageable) {
        Page<Beer> resultListOfBeers = service.findAllBeersPage(pageable);
        return ResponseEntity.ok().body(resultListOfBeers);
    }

    @GetMapping("beer/{beerId}")
    public ResponseEntity<Beer> findProperBeerByBeerId(@PathVariable Long beerId) throws NoContentException {
        Beer resultBeer = service.findBeerByBeerId(beerId);
        return ResponseEntity.ok().body(resultBeer);
    }

    @GetMapping("brewery/{breweryId}/beer")
    public ResponseEntity<Page<Beer>> findAllBeersByBreweryId(@PathVariable Long breweryId, Pageable pageable) throws NoContentException {
        Page<Beer> resultListOfBeers = service.findAllBeersByBreweryId(breweryId,pageable);
        return ResponseEntity.ok().body(resultListOfBeers);
    }

    @GetMapping("brewery/{breweryId}/beer/{beerId}")
    public ResponseEntity<Beer> findProperBeerBaseOnBreweryIdAndBeerId(@PathVariable Long breweryId, @PathVariable Long beerId) throws NoContentException {
        Beer resultBeer = service.findProperBeerByBreweryIdAndBeerId(breweryId, beerId);
        return ResponseEntity.ok().body(resultBeer);
    }

    //post methods

    @PostMapping("beer")
    public ResponseEntity<Beer> addNewBeer(@RequestBody Beer beer) throws URISyntaxException {
            Beer resultBeer = service.addNewBeerToRepository(beer);
            return ResponseEntity.created(new URI("add-beer" + resultBeer.getBeerId()))
                    .body(resultBeer);
    }

    @PostMapping("brewery/{breweryId}/beer")
    public ResponseEntity<Beer> AddNewBeerAssignedToBreweryByBreweryId(@PathVariable Long breweryId, @RequestBody Beer beer) throws NoContentException {
        Beer resultBeer = service.addNewBeerAssignedToBreweryByBreweryId(breweryId,beer);
        return ResponseEntity.ok().body(resultBeer);
    }

    //put methods

    @PutMapping("beer/{beerId}")
    public ResponseEntity<Beer> updateBeer(@PathVariable Long beerId, @RequestBody Beer beer) throws NoContentException {
            Beer resultBeer = service.updateBeerByBeerId(beerId,beer);
            return ResponseEntity.ok().body(resultBeer);
    }

    @PutMapping("brewery/{breweryId}/beer/{beerId}")
    public ResponseEntity<Beer> updateBeerBaseOnBreweryIdAndBeerId(@PathVariable Long breweryId,
                                                                   @PathVariable Long beerId,
                                                                   @RequestBody Beer beer) throws NoContentException {
        Beer resultBeer = service.updateBeerByBreweryIdAndBeerId(breweryId,beerId,beer);
        return ResponseEntity.ok().body(resultBeer);
    }

    //delete methods

    @DeleteMapping("beer/{beerId}")
    public ResponseEntity deleteBeerById(@PathVariable Long beerId) throws NoContentException {
        service.deleteBeerByBeerId(beerId);
        return ResponseEntity.noContent().build();
    }
}
