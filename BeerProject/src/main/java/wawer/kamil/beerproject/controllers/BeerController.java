package wawer.kamil.beerproject.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wawer.kamil.beerproject.domain.Beer;
import wawer.kamil.beerproject.dto.BeerDTO;
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
    private final ModelMapper mapper;

    //get methods

    @GetMapping("beer")
    public ResponseEntity<Page<Beer>> findAllBeers(Pageable pageable) {
        Page<Beer> resultListOfBeers = service.findAllBeersPage(pageable);
        return ResponseEntity.ok().body(resultListOfBeers);
    }

    @GetMapping("brewery/{breweryId}/beer")
    public ResponseEntity<Page<Beer>> findAllBeersByBreweryId(@PathVariable Long breweryId, Pageable pageable) throws NoContentException {
        Page<Beer> resultListOfBeers = service.findAllBeersByBreweryId(breweryId, pageable);
        return ResponseEntity.ok().body(resultListOfBeers);
    }

    @GetMapping("beer/{beerId}")
    public ResponseEntity<BeerDTO> findProperBeerByBeerId(@PathVariable Long beerId) throws NoContentException {
        BeerDTO resultBeerDTO = mapper.map(service.findBeerByBeerId(beerId), BeerDTO.class);
        return ResponseEntity.ok().body(resultBeerDTO);
    }

    @GetMapping("brewery/{breweryId}/beer/{beerId}")
    public ResponseEntity<BeerDTO> findProperBeerBaseOnBreweryIdAndBeerId(@PathVariable Long breweryId, @PathVariable Long beerId) throws NoContentException {
        BeerDTO resultBeerDTO = mapper.map(service.findProperBeerByBreweryIdAndBeerId(breweryId, beerId), BeerDTO.class);
        return ResponseEntity.ok().body(resultBeerDTO);
    }

    //post methods

    @PostMapping("beer")
    public ResponseEntity<BeerDTO> addNewBeer(@RequestBody BeerDTO beerDTO) throws URISyntaxException {
        Beer resultBeer = service.addNewBeerToRepository(mapper.map(beerDTO, Beer.class));
        return ResponseEntity.created(new URI("add-beer" + resultBeer.getBeerId()))
                .body(mapper.map(resultBeer, BeerDTO.class));
    }

    @PostMapping("brewery/{breweryId}/beer")
    public ResponseEntity<BeerDTO> addNewBeerAssignedToBreweryByBreweryId(@PathVariable Long breweryId, @RequestBody BeerDTO beerDTO) throws NoContentException, URISyntaxException {
        Beer resultBeer = service.addNewBeerAssignedToBreweryByBreweryId(breweryId, mapper.map(beerDTO, Beer.class));
        return ResponseEntity.created(new URI("add-beer" + resultBeer.getBeerId()))
                .body(mapper.map(resultBeer, BeerDTO.class));
    }

    //put methods

    @PutMapping("beer/{beerId}")
    public ResponseEntity<BeerDTO> updateBeer(@PathVariable Long beerId, @RequestBody BeerDTO beerDTO) throws NoContentException {
        Beer resultBeer = service.updateBeerByBeerId(beerId, mapper.map(beerDTO, Beer.class));
        return ResponseEntity.ok().body(mapper.map(resultBeer, BeerDTO.class));
    }

    @PutMapping("brewery/{breweryId}/beer/{beerId}")
    public ResponseEntity<BeerDTO> updateBeerBaseOnBreweryIdAndBeerId(@PathVariable Long breweryId,
                                                                   @PathVariable Long beerId,
                                                                   @RequestBody BeerDTO beerDTO) throws NoContentException {
        Beer resultBeer = service.updateBeerByBreweryIdAndBeerId(breweryId, beerId, mapper.map(beerDTO, Beer.class));
        return ResponseEntity.ok().body(mapper.map(resultBeer, BeerDTO.class));
    }

    //delete methods

    @DeleteMapping("beer/{beerId}")
    public ResponseEntity deleteBeerByBeerId(@PathVariable Long beerId) throws NoContentException {
        service.deleteBeerByBeerId(beerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("brewery/{breweryId}/beer/{beerId}")
    public ResponseEntity deleteBeerByBreweryIdAndBeerId(@PathVariable Long breweryId, @PathVariable Long beerId) throws NoContentException {
        service.deleteBeerByBreweryIdAndBeerId(breweryId, beerId);
        return ResponseEntity.noContent().build();
    }
}
