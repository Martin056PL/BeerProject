package wawer.kamil.beerproject.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@CrossOrigin
@Controller
@RestControllerAdvice
@Slf4j(topic = "application.logger")
public class BeerController {

    private final BeerService service;
    private final ModelMapper mapper;

    //get methods

    @GetMapping("beer")
    public ResponseEntity<Page<Beer>> findAllBeers(Pageable pageable) {
        log.debug("Endpoint address: 'beer' with GET method, request parameter - pageable: {}", pageable);
        Page<Beer> resultListOfBeers = service.findAllBeersPage(pageable);
        log.debug("List of returned Id: {}", resultListOfBeers.stream().map(Beer::getBeerId).collect(Collectors.toList()));
        return ResponseEntity.ok().body(resultListOfBeers);
    }

    @GetMapping("brewery/{breweryId}/beer")
    public ResponseEntity<Page<Beer>> findAllBeersByBreweryId(@PathVariable Long breweryId, Pageable pageable) throws NoContentException {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer' with GET method, request parameter - breweryId: {}; Pageable: {}", breweryId, pageable);
        Page<Beer> resultListOfBeers = service.findAllBeersByBreweryId(breweryId, pageable);
        log.debug("List of returned beerId: {}", resultListOfBeers.stream().map(Beer::getBeerId).collect(Collectors.toList()));
        return ResponseEntity.ok().body(resultListOfBeers);
    }

    @GetMapping("beer/{beerId}")
    public ResponseEntity<BeerDTO> findProperBeerByBeerId(@PathVariable Long beerId) throws NoContentException {
        log.debug("Endpoint address: 'beer/{beerId}' with GET method, request parameter - beerId: {}", beerId);
        BeerDTO resultBeerDTO = mapper.map(service.findBeerByBeerId(beerId), BeerDTO.class);
        return ResponseEntity.ok().body(resultBeerDTO);
    }

    @GetMapping("brewery/{breweryId}/beer/{beerId}")
    public ResponseEntity<BeerDTO> findProperBeerBaseOnBreweryIdAndBeerId(@PathVariable Long breweryId, @PathVariable Long beerId) throws NoContentException {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer/{beerId}' with GET method, request parameter - breweryId: {}, beerId: {}", breweryId, beerId);
        BeerDTO resultBeerDTO = mapper.map(service.findProperBeerByBreweryIdAndBeerId(breweryId, beerId), BeerDTO.class);
        return ResponseEntity.ok().body(resultBeerDTO);
    }

    //post methods

    @PostMapping("beer")
    public ResponseEntity<BeerDTO> addNewBeer(@RequestBody BeerDTO beerDTO) throws URISyntaxException {
        log.debug("Endpoint address: 'beer' with POST method, request parameter -  beerData: Name: {}; Style: {}; Alcohol: {}; Extract: {}"
                , beerDTO.getName()
                , beerDTO.getStyle()
                , beerDTO.getAlcohol()
                , beerDTO.getExtract());
        Beer resultBeer = service.addNewBeerToRepository(mapper.map(beerDTO, Beer.class));
        log.debug("Add new beer with Id: {}", resultBeer.getBeerId());
        return ResponseEntity.created(new URI("add-beer" + resultBeer.getBeerId()))
                .body(mapper.map(resultBeer, BeerDTO.class));
    }

    @PostMapping("brewery/{breweryId}/beer")
    public ResponseEntity<BeerDTO> addNewBeerAssignedToBreweryByBreweryId(@PathVariable Long breweryId, @RequestBody BeerDTO beerDTO) throws NoContentException, URISyntaxException {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer' with POST method, request parameter - breweryId: {}, beerData: Name: {}; Style: {}; Alcohol: {}; Extract: {}"
                , breweryId
                , beerDTO.getName()
                , beerDTO.getStyle()
                , beerDTO.getAlcohol()
                , beerDTO.getExtract());
        Beer resultBeer = service.addNewBeerAssignedToBreweryByBreweryId(breweryId, mapper.map(beerDTO, Beer.class));
        log.debug("Add new beer with Id: {}", resultBeer.getBeerId());
        return ResponseEntity.created(new URI("add-beer" + resultBeer.getBeerId()))
                .body(mapper.map(resultBeer, BeerDTO.class));
    }

    //put methods

    @PutMapping("beer/{beerId}")
    public ResponseEntity<BeerDTO> updateBeer(@PathVariable Long beerId, @RequestBody BeerDTO beerDTO) throws NoContentException {
        log.debug("Endpoint address: 'beer/{beerId}' with PUT method, request parameter - breweryId: {}, beerData: Name: {}; Style: {}; Alcohol: {}; Extract: {}"
                , beerId
                , beerDTO.getName()
                , beerDTO.getStyle()
                , beerDTO.getAlcohol()
                , beerDTO.getExtract());
        Beer resultBeer = service.updateBeerByBeerId(beerId, mapper.map(beerDTO, Beer.class));
        log.debug("Updated beer with Id: {}", resultBeer.getBeerId());
        return ResponseEntity.ok().body(mapper.map(resultBeer, BeerDTO.class));
    }

    @PutMapping("brewery/{breweryId}/beer/{beerId}")
    public ResponseEntity<BeerDTO> updateBeerBaseOnBreweryIdAndBeerId(@PathVariable Long breweryId,
                                                                      @PathVariable Long beerId,
                                                                      @RequestBody BeerDTO beerDTO) throws NoContentException {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer/{beerId}' with PUT method, request parameter - breweryId: {}, beerId: {} beerData: Name: {}; Style: {}; Alcohol: {}; Extract: {}"
                , breweryId
                , beerId
                , beerDTO.getName()
                , beerDTO.getStyle()
                , beerDTO.getAlcohol()
                , beerDTO.getExtract());
        Beer resultBeer = service.updateBeerByBreweryIdAndBeerId(breweryId, beerId, mapper.map(beerDTO, Beer.class));
        log.debug("Updated beer with Id: {}", resultBeer.getBeerId());
        return ResponseEntity.ok().body(mapper.map(resultBeer, BeerDTO.class));
    }

    //delete methods

    @DeleteMapping("beer/{beerId}")
    public ResponseEntity deleteBeerByBeerId(@PathVariable Long beerId) throws NoContentException {
        log.debug("Endpoint address: 'beer/{beerId}' with DELETE method, request parameter - beerId: {}", beerId);
        service.deleteBeerByBeerId(beerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("brewery/{breweryId}/beer/{beerId}")
    public ResponseEntity deleteBeerByBreweryIdAndBeerId(@PathVariable Long breweryId, @PathVariable Long beerId) throws NoContentException {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer/{beerId}' with DELETE method, request parameter - breweryId: {}, beerId: {}", breweryId, beerId);
        service.deleteBeerByBreweryIdAndBeerId(breweryId, beerId);
        return ResponseEntity.noContent().build();
    }
}
