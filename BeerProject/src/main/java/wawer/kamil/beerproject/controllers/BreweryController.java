package wawer.kamil.beerproject.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.dto.request.BreweryRequest;
import wawer.kamil.beerproject.dto.response.BreweryResponse;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.service.BreweryService;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("brewery")
@RequiredArgsConstructor
@Slf4j(topic = "application.logger")
public class BreweryController {

    private final BreweryService service;

    @GetMapping
    public ResponseEntity<Page<BreweryResponse>> getAllBreweryPage(Pageable pageable) {
        Page<BreweryResponse> listOfBrewery = service.getAllBreweryPage(pageable);
        log.debug("List of returned Id: {}", listOfBrewery.stream().map(BreweryResponse::getId).collect(Collectors.toList()));
        return ok().body(listOfBrewery);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BreweryResponse>> getAllBreweryList() {
        List<BreweryResponse> listOfBrewery = service.getAllBreweryList();
        log.debug("List of returned Id: {}", listOfBrewery.stream().map(BreweryResponse::getId).collect(Collectors.toList()));
        return ok().body(listOfBrewery);
    }

    @GetMapping("{id}")
    public ResponseEntity<BreweryResponse> getBreweryById(@PathVariable Long id) throws ElementNotFoundException {
        BreweryResponse breweryResponse = service.findBreweryById(id);
        return ok().body(breweryResponse);
    }

    @PostMapping
    public ResponseEntity<BreweryResponse> addNewBrewery(@RequestBody BreweryRequest breweryRequest) throws URISyntaxException {
        BreweryResponse savedBrewery = service.createNewBrewery(breweryRequest);
        log.debug("Add new brewery with Id: {}", savedBrewery.getId());
        return created(new URI("add-beer/" + savedBrewery.getId())).body(savedBrewery);
    }

    @PutMapping("{id}")
    public ResponseEntity<BreweryResponse> updateBrewery(@PathVariable Long id, @RequestBody BreweryRequest breweryRequest) throws ElementNotFoundException {
        BreweryResponse updatedBrewery = service.updateBreweryById(id, breweryRequest);
        log.debug("Updated brewery with Id: {}", updatedBrewery.getId());
        return ok().body(updatedBrewery);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteBrewery(@PathVariable Long id) throws ElementNotFoundException {
        service.deleteBreweryById(id);
        log.debug("Deleted brewery with Id: {}", id);
        return noContent().build();
    }

    @GetMapping(value = "{id}/image", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> downloadImage(@PathVariable Long id) throws ElementNotFoundException {
        byte[] image = service.getBreweryImageFromDbBaseOnBreweryId(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return ok().headers(headers).body(image);
    }

    @PostMapping(value = "{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImage(@PathVariable Long id, @RequestParam(name = "image") MultipartFile file) throws IOException, ElementNotFoundException, InvalidImageParameters {
        service.setBreweryImageToProperBreweryBaseOnBreweryId(id, file);
        return ok().body("File is uploaded successfully");
    }


}
