package wawer.kamil.beerproject.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.dto.request.BreweryRequest;
import wawer.kamil.beerproject.dto.response.BreweryResponse;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.service.BreweryService;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("breweries")
@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j(topic = "application.logger")
public class BreweryController {

    private final BreweryService service;

    @GetMapping("/page")
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'EXHIBITOR')")
    public ResponseEntity<Page<BreweryResponse>> getAllBreweryPage(Pageable pageable) {
        Page<BreweryResponse> listOfBrewery = service.getAllBreweryPage(pageable);
        return ok().body(listOfBrewery);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'EXHIBITOR')")
    public ResponseEntity<List<BreweryResponse>> getAllBreweryList() {
        List<BreweryResponse> listOfBrewery = service.getAllBreweryList();
        return ok().body(listOfBrewery);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'EXHIBITOR')")
    public ResponseEntity<BreweryResponse> getBreweryById(@RequestParam Long id)  {
        BreweryResponse breweryResponse = service.findBreweryById(id);
        return ok().body(breweryResponse);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'EXHIBITOR')")
    public ResponseEntity<BreweryResponse> addNewBrewery(@RequestBody @Valid BreweryRequest breweryRequest) throws URISyntaxException {
        BreweryResponse savedBrewery = service.createNewBrewery(breweryRequest);
        return created(new URI("add-beer/" + savedBrewery.getId())).body(savedBrewery);
    }

    @PutMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'EXHIBITOR')")
    public ResponseEntity<BreweryResponse> updateBrewery(@RequestParam(name = "breweryId") Long id, @Valid @RequestBody BreweryRequest breweryRequest)  {
        BreweryResponse updatedBrewery = service.updateBreweryById(id, breweryRequest);
        return ok().body(updatedBrewery);
    }

    @DeleteMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'EXHIBITOR')")
    public ResponseEntity<Object> deleteBrewery(@RequestParam(name = "breweryId") Long id)  {
        service.deleteBreweryById(id);
        return noContent().build();
    }

    @GetMapping(value = "/image", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('USER','ADMIN', 'EXHIBITOR')")
    public ResponseEntity<Object> downloadImage(@RequestParam("breweryId") Long id)  {
        byte[] image = service.getBreweryImageFromDbBaseOnBreweryId(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return ok().headers(headers).body(image);
    }

    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'EXHIBITOR')")
    public ResponseEntity<Object> uploadImage(@RequestParam("breweryId") Long id, @RequestParam(name = "image") MultipartFile file) throws IOException {
        service.setBreweryImageToProperBreweryBaseOnBreweryId(id, file);
        return ok().body("File is uploaded successfully");
    }


}
