package wawer.kamil.beerproject.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wawer.kamil.beerproject.domain.Brewery;
import wawer.kamil.beerproject.dto.BreweryDTO;
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
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<Page<BreweryDTO>> getAllBrewery(Pageable pageable) throws NoContentException {
        Page<BreweryDTO> listOfBeers = service.getAllBrewery(pageable).map(brewery -> mapper.map(brewery, BreweryDTO.class));
        return ResponseEntity.ok(listOfBeers);
    }

    @GetMapping("{breweryId}")
    public ResponseEntity<BreweryDTO> getBreweryByBreweryId(@PathVariable Long breweryId) throws NoContentException {
        BreweryDTO brewery = mapper.map(service.getBreweryByBreweryId(breweryId), BreweryDTO.class);
        return ResponseEntity.ok().body(brewery);
    }

    @PostMapping
    public ResponseEntity<BreweryDTO> addNewBrewery(@RequestBody BreweryDTO breweryDTO) throws URISyntaxException {
        Brewery result = service.createNewBrewery(mapper.map(breweryDTO, Brewery.class));
        return ResponseEntity.created(new URI("add-beer" + result.getBreweryId()))
                .body(mapper.map(result, BreweryDTO.class));
    }

    @PutMapping("{breweryId}")
    public ResponseEntity<BreweryDTO> updateBrewery(@PathVariable Long breweryId, @RequestBody BreweryDTO breweryDTO) throws NoContentException {
        Brewery result = service.updateBreweryById(breweryId,mapper.map(breweryDTO, Brewery.class));
        return ResponseEntity.ok().body(mapper.map(result, BreweryDTO.class));
    }

    @DeleteMapping("{breweryId}")
    public ResponseEntity deleteBrewery(@PathVariable Long breweryId) throws NoContentException {
        service.deleteBreweryByBreweryId(breweryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
