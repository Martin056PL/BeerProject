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
import java.util.Optional;

@RequiredArgsConstructor
@CrossOrigin
@Controller
@RequestMapping("beer")
public class BeerController {

    private final BeerService service;

    @GetMapping
    public ResponseEntity<Page<Beer>> getAllBeers(Pageable pageable) throws NoContentException {
        Page<Beer> listOfBeers = service.findAllBeersPage(pageable);
        return ResponseEntity.ok(listOfBeers);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<Beer> getBeerByBeerId(@PathVariable Long beerId) throws NoContentException {
        Beer beer = service.findBeerByBeerId(beerId);
        return ResponseEntity.ok(beer);
    }

    @PostMapping
    public ResponseEntity<Beer> addNewBeer(@RequestBody Beer beer) throws URISyntaxException {
        Optional<Beer> requestBodyBeer = Optional.ofNullable(beer);
        if (requestBodyBeer.isPresent()) {
            Beer result = service.addNewBeerToRepository(beer);
            return ResponseEntity.created(new URI("add-movie" + result.getBeerId()))
                    .body(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{beerId}")
    public ResponseEntity<Beer> updateBeer(@PathVariable Long beerId, @RequestBody Beer beer) throws NoContentException {
        Optional<Beer> requestBodyBeer = Optional.ofNullable(beer);
        if(requestBodyBeer.isPresent()) {
            Beer result = service.updateBeerByBeerID(beerId,beer);
            return ResponseEntity.ok(result);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("{beerId}")
    public ResponseEntity deleteBeerById(@PathVariable Long beerId) throws NoContentException {
        service.deleteBeerByBeerId(beerId);
        return ResponseEntity.noContent().build();
    }
}
