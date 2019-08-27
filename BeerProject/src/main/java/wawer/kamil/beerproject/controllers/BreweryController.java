package wawer.kamil.beerproject.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.domain.Brewery;
import wawer.kamil.beerproject.dto.BreweryDTO;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.service.BreweryService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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
    public ResponseEntity<Page<Brewery>> getAllBreweryPage(Pageable pageable) throws NoContentException {
        log.debug("Endpoint address: 'brewery' with GET method, request parameter - pageable: {}", pageable);
        Page<Brewery> listOfBrewery = service.getAllBreweryPage(pageable);
        log.debug("List of returned Id: {}", listOfBrewery.stream().map(Brewery::getBreweryId).collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.OK).body(listOfBrewery);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Brewery>> getAllBreweryList() {
        log.debug("Endpoint address: 'brewery/list' with GET method");
        List<Brewery> listOfBrewery = service.getAllBreweryList();
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
                , breweryDTO.getName()
                , breweryDTO.getWebsite()
                , breweryDTO.getEmail()
                , breweryDTO.getPhoneNumber());
        Brewery result = service.createNewBrewery(mapper.map(breweryDTO, Brewery.class));
        log.debug("Add new brewery with Id: {}", result.getBreweryId());
        return ResponseEntity.created(new URI("add-beer" + result.getBreweryId()))
                .body(mapper.map(result, BreweryDTO.class));
    }

    @PutMapping("{breweryId}")
    public ResponseEntity<BreweryDTO> updateBrewery(@PathVariable Long breweryId, @RequestBody BreweryDTO breweryDTO) throws NoContentException {
        log.debug("Endpoint address: 'brewery/{breweryId}' with PUT method, request parameter - brewery id: {};  brewery data: {}; {}; {}; {}"
                , breweryId
                , breweryDTO.getName()
                , breweryDTO.getWebsite()
                , breweryDTO.getEmail()
                , breweryDTO.getPhoneNumber());
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

    @PostMapping(value = "{breweryId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImage(@PathVariable Long breweryId, @RequestParam(name = "file") MultipartFile file) throws IOException, NoContentException {
        service.setBreweryImageToProperBreweryBaseOnBreweryId(breweryId, file);
        return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
    }

    @GetMapping(value = "{breweryId}/download", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity <Object> downloadImage(@PathVariable Long breweryId) throws NoContentException {
        byte [] image = service.getBreweryImageFromDbBaseOnBreweryId(breweryId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity (image, headers, HttpStatus.OK);
    }
}
