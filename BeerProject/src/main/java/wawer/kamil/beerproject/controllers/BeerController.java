package wawer.kamil.beerproject.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.dto.BeerDTO;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.service.BeerService;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RestControllerAdvice
@Slf4j(topic = "application.logger")
public class BeerController {

    private final BeerService service;
    private final ModelMapper mapper;

    //get methods

    @GetMapping("beer")
    @PreAuthorize("hasAnyAuthority('user:read','admin:read')")
    public ResponseEntity<Page<BeerDTO>> findAllBeersPage(Pageable pageable) {
        Page<Beer> resultListOfBeers = service.findAllBeersPage(pageable);
        Page<BeerDTO> resultListOfBeersDTO = resultListOfBeers.map(beer -> mapper.map(beer, BeerDTO.class));
        log.debug("List of returned Id: {}", resultListOfBeers.stream().map(Beer::getBeerId).collect(Collectors.toList()));
        return ok().body(resultListOfBeersDTO);
    }

    @GetMapping("beer/list")
    public ResponseEntity<List<BeerDTO>> findAllBeersList() {
        List<Beer> resultListOfBeers = service.findAllBeersList();
        List<BeerDTO> resultListOfBeersDTO = resultListOfBeers.stream().map(beer -> mapper.map(beer, BeerDTO.class)).collect(Collectors.toList());
        log.debug("List of returned Id: {}", resultListOfBeers.stream().map(Beer::getBeerId).collect(Collectors.toList()));
        return ok().body(resultListOfBeersDTO);
    }

    @GetMapping("brewery/{breweryId}/beer")
    public ResponseEntity<Page<BeerDTO>> findAllBeersByBreweryIdPage(@PathVariable Long breweryId, Pageable pageable) throws NoContentException {
        Page<Beer> resultListOfBeers = service.findAllBeersByBreweryIdPage(breweryId, pageable);
        Page<BeerDTO> resultListOfBeersDTO = resultListOfBeers.map(beer -> mapper.map(beer, BeerDTO.class));
        log.debug("List of returned beerId: {}", resultListOfBeers.stream().map(Beer::getBeerId).collect(Collectors.toList()));
        return ok().body(resultListOfBeersDTO);
    }

    @GetMapping("brewery/{breweryId}/beer/list")
    public ResponseEntity<List<BeerDTO>> findAllBeersByBreweryIdList(@PathVariable Long breweryId) throws NoContentException {
        List<Beer> resultListOfBeers = service.findAllBeersByBreweryIdList(breweryId);
        List<BeerDTO> resultListOfBeersDTO = resultListOfBeers.stream().map(beer -> mapper.map(beer, BeerDTO.class)).collect(Collectors.toList());
        log.debug("List of returned beerId: {}", resultListOfBeers.stream().map(Beer::getBeerId).collect(Collectors.toList()));
        return ok().body(resultListOfBeersDTO);
    }

    @GetMapping("beer/{beerId}")
    public ResponseEntity<BeerDTO> findProperBeerByBeerId(@PathVariable Long beerId) throws NoContentException {
        BeerDTO resultBeerDTO = mapper.map(service.findBeerByBeerId(beerId), BeerDTO.class);
        return ok().body(resultBeerDTO);
    }

    @GetMapping("brewery/{breweryId}/beer/{beerId}")
    public ResponseEntity<BeerDTO> findProperBeerBaseOnBreweryIdAndBeerId(@PathVariable Long breweryId, @PathVariable Long beerId) throws NoContentException {
        BeerDTO resultBeerDTO = mapper.map(service.findProperBeerByBreweryIdAndBeerId(breweryId, beerId), BeerDTO.class);
        return ok().body(resultBeerDTO);
    }

    //post methods

    @PostMapping("beer")
    public ResponseEntity<BeerDTO> addNewBeer(@RequestBody BeerDTO beerDTO) throws URISyntaxException {
        Beer resultBeer = service.addNewBeerToRepository(mapper.map(beerDTO, Beer.class));
        log.debug("Add new beer with Id: {}", resultBeer.getBeerId());
        return created(new URI("add-beer" + resultBeer.getBeerId())).body(mapper.map(resultBeer, BeerDTO.class));
    }

    @PostMapping("brewery/{breweryId}/beer")
    public ResponseEntity<BeerDTO> addNewBeerAssignedToBreweryByBreweryId(@PathVariable Long breweryId, @Valid @RequestBody BeerDTO beerDTO) throws NoContentException, URISyntaxException {
        Beer resultBeer = service.addNewBeerAssignedToBreweryByBreweryId(breweryId, mapper.map(beerDTO, Beer.class));
        log.debug("Add new beer with Id: {}", resultBeer.getBeerId());
        return created(new URI("add-beer" + resultBeer.getBeerId())).body(mapper.map(resultBeer, BeerDTO.class));
    }

    //put methods

    @PutMapping("beer/{beerId}")
    public ResponseEntity<BeerDTO> updateBeer(@PathVariable Long beerId, @Valid @RequestBody BeerDTO beerDTO) throws NoContentException {
        Beer resultBeer = service.updateBeerByBeerId(beerId, mapper.map(beerDTO, Beer.class));
        log.debug("Updated beer with Id: {}", resultBeer.getBeerId());
        return ok().body(mapper.map(resultBeer, BeerDTO.class));
    }

    @PutMapping("brewery/{breweryId}/beer/{beerId}")
    public ResponseEntity<BeerDTO> updateBeerBaseOnBreweryIdAndBeerId(@PathVariable Long breweryId,
                                                                      @PathVariable Long beerId,
                                                                      @Valid @RequestBody BeerDTO beerDTO) throws NoContentException {
        Beer resultBeer = service.updateBeerByBreweryIdAndBeerId(breweryId, beerId, mapper.map(beerDTO, Beer.class));
        log.debug("Updated beer with Id: {}", resultBeer.getBeerId());
        return ok().body(mapper.map(resultBeer, BeerDTO.class));
    }

    //delete methods

    @DeleteMapping("beer/{beerId}")
    public ResponseEntity deleteBeerByBeerId(@PathVariable Long beerId) throws NoContentException {
        service.deleteBeerByBeerId(beerId);
        return noContent().build();
    }

    @DeleteMapping("brewery/{breweryId}/beer/{beerId}")
    public ResponseEntity deleteBeerByBreweryIdAndBeerId(@PathVariable Long breweryId, @PathVariable Long beerId) throws NoContentException {
        service.deleteBeerByBreweryIdAndBeerId(breweryId, beerId);
        return noContent().build();
    }

    @PostMapping(value = "brewery/{breweryId}/beer/{beerId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImage(@PathVariable Long breweryId, @PathVariable Long beerId, @RequestParam(name = "image") MultipartFile file) throws IOException, NoContentException, InvalidImageParameters {
        service.setBeerImageToProperBeerBaseOnBeerId(breweryId, beerId, file);
        return ok().body("File is uploaded successfully");
    }

    @GetMapping(value = "brewery/{breweryId}/beer/{beerId}/image", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long breweryId, @PathVariable Long beerId) throws NoContentException {
        byte[] image = service.getBeerImageFromDbBaseOnBreweryIdAndBeerId(breweryId, beerId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return ok().headers(headers).body(image);
    }
}
