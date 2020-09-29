package wawer.kamil.beerproject.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wawer.kamil.beerproject.dto.request.PageParams;
import wawer.kamil.beerproject.dto.response.BreweryResponse;
import wawer.kamil.beerproject.service.BreweryService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static wawer.kamil.beerproject.dto.request.PageParams.wrapRequestParams;

@RestController
@RequestMapping("brewery")
@Slf4j(topic = "application.logger")
public class BreweryController {

    private final BreweryService service;

    public BreweryController(BreweryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BreweryResponse>> getAllBreweryPage(@RequestParam(required = false) Integer pageNumber,
                                                                   @RequestParam(required = false) Integer pageSize,
                                                                   @RequestParam(required = false) Sort.Direction sortDirection,
                                                                   @RequestParam(required = false) String sortBy) {
        PageParams pageParams = wrapRequestParams(pageNumber, pageSize, sortDirection, sortBy);
        // log.debug("List of returned Id: {}", listOfBrewery.stream().map(Brewery::getBreweryId).collect(Collectors.toList()));
        return ok().body(service.getAllBreweryPage(pageParams));
    }


//
//    @GetMapping("/list")
//    public ResponseEntity<List<BreweryDTO>> getAllBreweryList() {
//        List<Brewery> listOfBrewery = service.getAllBreweryList();
//        List<BreweryDTO> listOfBreweryDTO = listOfBrewery.stream().map(brewery -> mapper.map(brewery, BreweryDTO.class)).collect(Collectors.toList());
//        log.debug("List of returned Id: {}", listOfBrewery.stream().map(Brewery::getBreweryId).collect(Collectors.toList()));
//        return ok().body(listOfBreweryDTO);
//    }
//
//    @GetMapping("{breweryId}")
//    public ResponseEntity<BreweryDTO> getBreweryByBreweryId(@PathVariable Long breweryId) throws NoContentException {
//        BreweryDTO brewery = mapper.map(service.getBreweryByBreweryId(breweryId), BreweryDTO.class);
//        return ok().body(brewery);
//    }
//
//    @PostMapping
//    public ResponseEntity<BreweryDTO> addNewBrewery(@Valid @RequestBody BreweryDTO breweryDTO) throws URISyntaxException {
//        Brewery result = service.createNewBrewery(mapper.map(breweryDTO, Brewery.class));
//        log.debug("Add new brewery with Id: {}", result.getBreweryId());
//        return created(new URI("add-beer" + result.getBreweryId())).body(mapper.map(result, BreweryDTO.class));
//    }
//
//    @PutMapping("{breweryId}")
//    public ResponseEntity<BreweryDTO> updateBrewery(@PathVariable Long breweryId, @Valid @RequestBody BreweryDTO breweryDTO) throws NoContentException {
//        Brewery result = service.updateBreweryById(breweryId, mapper.map(breweryDTO, Brewery.class));
//        log.debug("Updated brewery with Id: {}", result.getBreweryId());
//        return ok().body(mapper.map(result, BreweryDTO.class));
//    }
//
//    @DeleteMapping("{breweryId}")
//    public ResponseEntity deleteBrewery(@PathVariable Long breweryId) throws NoContentException {
//        service.deleteBreweryByBreweryId(breweryId);
//        log.debug("Deleted brewery with Id: {}", breweryId);
//        return noContent().build();
//    }
//
//    @PostMapping(value = "{breweryId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Object> uploadImage(@PathVariable Long breweryId, @RequestParam(name = "image") MultipartFile file) throws IOException, NoContentException, InvalidImageParameters {
//        service.setBreweryImageToProperBreweryBaseOnBreweryId(breweryId, file);
//        return ok().body("File is uploaded successfully");
//    }
//
//    @GetMapping(value = "{breweryId}/image", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Object> downloadImage(@PathVariable Long breweryId) throws NoContentException {
//        byte[] image = service.getBreweryImageFromDbBaseOnBreweryId(breweryId);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_JPEG);
//        return ok().headers(headers).body(image);
//    }
}
