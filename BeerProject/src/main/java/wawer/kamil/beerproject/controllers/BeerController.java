package wawer.kamil.beerproject.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.domain.Beer;
import wawer.kamil.beerproject.dto.BeerDTO;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.service.BeerService;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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
    public ResponseEntity<Page<Beer>> findAllBeersPage(Pageable pageable) {
        log.debug("Endpoint address: 'beer' with GET method, request parameter - pageable: {}", pageable);
        Page<Beer> resultListOfBeers = service.findAllBeersPage(pageable);
        log.debug("List of returned Id: {}", resultListOfBeers.stream().map(Beer::getBeerId).collect(Collectors.toList()));
        return ResponseEntity.ok().body(resultListOfBeers);
    }

    @GetMapping("beer/list")
    public ResponseEntity<List<Beer>> findAllBeersList() {
        log.debug("Endpoint address: 'beer/list' with GET method");
        List<Beer> resultListOfBeers = service.findAllBeersList();
        log.debug("List of returned Id: {}", resultListOfBeers.stream().map(Beer::getBeerId).collect(Collectors.toList()));
        return ResponseEntity.ok().body(resultListOfBeers);
    }

    @GetMapping("brewery/{breweryId}/beer")
    public ResponseEntity<Page<Beer>> findAllBeersByBreweryIdPage(@PathVariable Long breweryId, Pageable pageable) throws NoContentException {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer' with GET method, request parameter - breweryId: {}; Pageable: {}", breweryId, pageable);
        Page<Beer> resultListOfBeers = service.findAllBeersByBreweryIdPage(breweryId, pageable);
        log.debug("List of returned beerId: {}", resultListOfBeers.stream().map(Beer::getBeerId).collect(Collectors.toList()));
        return ResponseEntity.ok().body(resultListOfBeers);
    }

    @GetMapping("brewery/{breweryId}/beer/list")
    public ResponseEntity<List<Beer>> findAllBeersByBreweryIdList(@PathVariable Long breweryId) throws NoContentException {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer/list' with GET method, request parameter - breweryId: {}", breweryId);
        List<Beer> resultListOfBeers = service.findAllBeersByBreweryIdList(breweryId);
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
    public ResponseEntity<BeerDTO> addNewBeerAssignedToBreweryByBreweryId(@PathVariable Long breweryId, @Valid @RequestBody BeerDTO beerDTO) throws NoContentException, URISyntaxException {
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
    public ResponseEntity<BeerDTO> updateBeer(@PathVariable Long beerId, @Valid @RequestBody BeerDTO beerDTO) throws NoContentException {
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
                                                                      @Valid @RequestBody BeerDTO beerDTO) throws NoContentException {
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

    @PostMapping(value = "brewery/{breweryId}/beer/{beerId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImage(@PathVariable Long breweryId, @PathVariable Long beerId, @RequestParam(name = "image") MultipartFile file) throws IOException, NoContentException, InvalidImageParameters {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer/{beerId}/image' with POST method, request parameter - breweryId: {}, beerId: {}", breweryId, beerId);
        service.setBeerImageToProperBeerBaseOnBeerId(breweryId, beerId, file);
        return ResponseEntity.ok().body("File is uploaded successfully");
    }

    @GetMapping(value = "brewery/{breweryId}/beer/{beerId}/image", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long breweryId, @PathVariable Long beerId) throws NoContentException {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer/{beerId}/image' with GET method, request parameter - breweryId: {}, beerId: {}", breweryId, beerId);
        byte [] image = service.getBeerImageFromDbBaseOnBreweryIdAndBeerId(breweryId, beerId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return ResponseEntity.ok().headers(headers).body(image);
    }
}
