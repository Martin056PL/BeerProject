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
import wawer.kamil.beerproject.domain.Brewery;
import wawer.kamil.beerproject.dto.BreweryDTO;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.service.BreweryService;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.*;

@RequiredArgsConstructor
@Controller
@CrossOrigin
@RequestMapping("brewery")
@Slf4j(topic = "application.logger")
public class BreweryController {

    private final BreweryService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<Page<BreweryDTO>> getAllBreweryPage(Pageable pageable) throws NoContentException {
        log.debug("Endpoint address: 'brewery' with GET method, request parameter - pageable: {}", pageable);
        Page<Brewery> listOfBrewery = service.getAllBreweryPage(pageable);
        Page<BreweryDTO> listOfBreweryDTO = listOfBrewery.map(brewery -> mapper.map(brewery, BreweryDTO.class));
        log.debug("List of returned Id: {}", listOfBrewery.stream().map(Brewery::getBreweryId).collect(Collectors.toList()));
        return ok().body(listOfBreweryDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BreweryDTO>> getAllBreweryList() {
        log.debug("Endpoint address: 'brewery/list' with GET method");
        List<Brewery> listOfBrewery = service.getAllBreweryList();
        List<BreweryDTO> listOfBreweryDTO = listOfBrewery.stream().map(brewery -> mapper.map(brewery, BreweryDTO.class)).collect(Collectors.toList());
        log.debug("List of returned Id: {}", listOfBrewery.stream().map(Brewery::getBreweryId).collect(Collectors.toList()));
        return ok().body(listOfBreweryDTO);
    }

    @GetMapping("{breweryId}")
    public ResponseEntity<BreweryDTO> getBreweryByBreweryId(@PathVariable Long breweryId) throws NoContentException {
        log.debug("Endpoint address: 'brewery/{breweryId}' with GET method, request parameter - id: {}", breweryId);
        BreweryDTO brewery = mapper.map(service.getBreweryByBreweryId(breweryId), BreweryDTO.class);
        return ok().body(brewery);
    }

    @PostMapping
    public ResponseEntity<BreweryDTO> addNewBrewery(@Valid @RequestBody BreweryDTO breweryDTO) throws URISyntaxException {
        log.debug("Endpoint address: 'brewery' with POST method, request parameter - brewery data: {}; {}; {}; {}"
                , breweryDTO.getName()
                , breweryDTO.getWebsite()
                , breweryDTO.getEmail()
                , breweryDTO.getPhoneNumber());
        Brewery result = service.createNewBrewery(mapper.map(breweryDTO, Brewery.class));
        log.debug("Add new brewery with Id: {}", result.getBreweryId());
        return  created(new URI("add-beer" + result.getBreweryId())).body(mapper.map(result, BreweryDTO.class));
    }

    @PutMapping("{breweryId}")
    public ResponseEntity<BreweryDTO> updateBrewery(@PathVariable Long breweryId, @Valid @RequestBody BreweryDTO breweryDTO) throws NoContentException {
        log.debug("Endpoint address: 'brewery/{breweryId}' with PUT method, request parameter - brewery id: {};  brewery data: {}; {}; {}; {}"
                , breweryId
                , breweryDTO.getName()
                , breweryDTO.getWebsite()
                , breweryDTO.getEmail()
                , breweryDTO.getPhoneNumber());
        Brewery result = service.updateBreweryById(breweryId, mapper.map(breweryDTO, Brewery.class));
        log.debug("Updated brewery with Id: {}", result.getBreweryId());
        return ok().body(mapper.map(result, BreweryDTO.class));
    }

    @DeleteMapping("{breweryId}")
    public ResponseEntity deleteBrewery(@PathVariable Long breweryId) throws NoContentException {
        log.debug("Endpoint address: 'brewery/{breweryId}' with DELETE method, request parameter - id: {}", breweryId);
        service.deleteBreweryByBreweryId(breweryId);
        log.debug("Deleted brewery with Id: {}", breweryId);
        return noContent().build();
    }

    @PostMapping(value = "{breweryId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImage(@PathVariable Long breweryId, @RequestParam(name = "image") MultipartFile file) throws IOException, NoContentException, InvalidImageParameters {
        service.setBreweryImageToProperBreweryBaseOnBreweryId(breweryId, file);
        return ok().body("File is uploaded successfully");
    }

    @GetMapping(value = "{breweryId}/image", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> downloadImage(@PathVariable Long breweryId) throws NoContentException {
        byte[] image = service.getBreweryImageFromDbBaseOnBreweryId(breweryId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return ok().headers(headers).body(image);
    }
}
