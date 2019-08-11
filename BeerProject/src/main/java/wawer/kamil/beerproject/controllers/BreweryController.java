package wawer.kamil.beerproject.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wawer.kamil.beerproject.domain.Brewery;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.service.BreweryService;

import java.net.URI;
import java.net.URISyntaxException;

@RequiredArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("brewery")
public class BreweryController {

    private final BreweryService service;

    @GetMapping
    public ResponseEntity<Page<Brewery>> getAllBrewery(Pageable pageable) throws NoContentException {
        Page<Brewery> listOfBeers = service.getAllBrewery(pageable);
        return ResponseEntity.ok(listOfBeers);
    }

    @GetMapping("{breweryId}")
    public ResponseEntity<Brewery> getBreweryByBreweryId(@PathVariable Long breweryId) throws NoContentException {
        Brewery brewery = service.getBreweryByBreweryId(breweryId);
        return ResponseEntity.ok().body(brewery);
    }

    @PostMapping
    public ResponseEntity<Brewery> addNewBrewery(@RequestBody Brewery brewery) throws URISyntaxException {
        Brewery result = service.createNewBrewery(brewery);
        return ResponseEntity.created(new URI("add-beer" + result.getBreweryId()))
                .body(result);
    }

    @PutMapping("{breweryId}")
    public ResponseEntity<Brewery> updateBrewery(@PathVariable Long breweryId, @RequestBody Brewery brewery) throws NoContentException {
        Brewery result = service.updateBreweryById(breweryId,brewery);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("{breweryId}")
    public ResponseEntity deleteBrewery(@PathVariable Long breweryId) throws NoContentException {
        service.deleteBreweryByBreweryId(breweryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
