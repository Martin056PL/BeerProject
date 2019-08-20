package wawer.kamil.beerproject.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("brewery")
@Slf4j(topic = "application.logger")
public class BreweryController {

    private final BreweryService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<Page<Brewery>> getAllBrewery(Pageable pageable) throws NoContentException {
        log.debug("Endpoint address: 'brewery' with GET method, request parameter - pageable: {}", pageable);
        Page<Brewery> listOfBrewery = service.getAllBrewery(pageable);
        log.debug("List of returned Id: {}", listOfBrewery.stream().map(Brewery::getBreweryId).collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.OK).body(listOfBrewery);
    }

    @GetMapping("{breweryId}")
    public ResponseEntity<BreweryDTO> getBreweryByBreweryId(@PathVariable Long breweryId) throws NoContentException {
        log.debug("Endpoint address: 'brewery/{breweryId}' with GET method, request parameter - id: {}", breweryId);
        BreweryDTO brewery = mapper.map(service.getBreweryByBreweryId(breweryId), BreweryDTO.class);
        return ResponseEntity.ok().body(brewery);
    }

    @PostMapping
    public ResponseEntity<BreweryDTO> addNewBrewery(@RequestBody BreweryDTO breweryDTO) throws URISyntaxException {
        log.debug("Endpoint address: 'brewery' with POST method, request parameter - brewery data: {}; {}; {}; {}"
                ,breweryDTO.getName()
                ,breweryDTO.getWebsite()
                ,breweryDTO.getEmail()
                ,breweryDTO.getPhoneNumber());
        Brewery result = service.createNewBrewery(mapper.map(breweryDTO, Brewery.class));
        log.debug("Add new brewery with Id: {}", result.getBreweryId());
        return ResponseEntity.created(new URI("add-beer" + result.getBreweryId()))
                .body(mapper.map(result, BreweryDTO.class));
    }

    @PutMapping("{breweryId}")
    public ResponseEntity<BreweryDTO> updateBrewery(@PathVariable Long breweryId, @RequestBody BreweryDTO breweryDTO) throws NoContentException {
        log.debug("Endpoint address: 'brewery/{breweryId}' with PUT method, request parameter - brewery id: {};  brewery data: {}; {}; {}; {}"
                ,breweryId
                ,breweryDTO.getName()
                ,breweryDTO.getWebsite()
                ,breweryDTO.getEmail()
                ,breweryDTO.getPhoneNumber());
        Brewery result = service.updateBreweryById(breweryId, mapper.map(breweryDTO, Brewery.class));
        log.debug("Updated brewery with Id: {}", result.getBreweryId());
        return ResponseEntity.ok().body(mapper.map(result, BreweryDTO.class));
    }

    @DeleteMapping("{breweryId}")
    public ResponseEntity deleteBrewery(@PathVariable Long breweryId) throws NoContentException {
        log.debug("Endpoint address: 'brewery/{breweryId}' with DELETE method, request parameter - id: {}", breweryId);
        service.deleteBreweryByBreweryId(breweryId);
        log.debug("Deleted brewery with Id: {}", breweryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
