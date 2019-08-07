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
import wawer.kamil.beerproject.service.BeerServiceImpl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RequiredArgsConstructor
@CrossOrigin
@Controller
@RequestMapping("/beer")
@RestControllerAdvice
public class BeerController {

    private final BeerService service;

    @GetMapping
    public ResponseEntity<Page<Beer>> getAllBeers(Pageable pageable) {
        Page<Beer> listOfBeers = service.findAllBeersPage(pageable);
        return ResponseEntity.ok().body(listOfBeers);
    }

    @GetMapping("{beerId}")
    public ResponseEntity<Beer> getBeerByBeerId(@PathVariable Long beerId) throws NoContentException {
        Beer beer = service.findBeerByBeerId(beerId);
        return ResponseEntity.ok().body(beer);
    }

    @PostMapping
    public ResponseEntity<Beer> addNewBeer(@RequestBody Beer beer) throws URISyntaxException {
        Optional<Beer> requestBodyBeer = Optional.ofNullable(beer);
        if (requestBodyBeer.isPresent()) {
            Beer result = service.addNewBeerToRepository(beer);
            return ResponseEntity.created(new URI("add-beer" + result.getBeerId()))
                    .body(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{beerId}")
    public ResponseEntity<Beer> updateBeer(@PathVariable Long beerId, @RequestBody Beer beer) throws NoContentException {
            Beer result = service.updateBeerByBeerID(beerId,beer);
            return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("{beerId}")
    public ResponseEntity deleteBeerById(@PathVariable Long beerId) throws NoContentException {
        service.deleteBeerByBeerId(beerId);
        return ResponseEntity.noContent().build();
    }
}
