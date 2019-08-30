package wawer.kamil.beerproject.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.domain.Brewery;
import wawer.kamil.beerproject.dto.BreweryDTO;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.service.BreweryServiceImpl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BreweryControllerTest {

    @Mock
    BreweryServiceImpl service;

    @Mock
    Page<Brewery> pageBrewery;

    @Mock
    Pageable pageable;

    @Mock
    Brewery brewery;

    @Mock
    BreweryDTO breweryDTO;

    @Mock
    ModelMapper mapper;

    @Mock
    MultipartFile file;

    @InjectMocks
    BreweryController controller;

    private final static Long ID = 1L;


    @Test
    public void should_return_status_code_which_equals_to_controller_status_code_brewery_page() throws NoContentException {
        when(service.getAllBreweryPage(pageable)).thenReturn(pageBrewery);
        assertEquals(HttpStatus.OK, controller.getAllBreweryPage(pageable).getStatusCode());
    }

    @Test
    public void should_return_response_entity_which_equals_to_controller_response_entity_base_on_brewery_id() throws NoContentException {
        when(mapper.map(service.getBreweryByBreweryId(ID), BreweryDTO.class)).thenReturn(breweryDTO);
        assertEquals(ResponseEntity.ok().body(breweryDTO), controller.getBreweryByBreweryId(ID));
    }

    @Test
    public void should_return_status_code_which_equals_to_controller_status_code_base_on_brewery_id() throws NoContentException {
        when(service.getBreweryByBreweryId(ID)).thenReturn(brewery);
        assertEquals(HttpStatus.OK, controller.getBreweryByBreweryId(ID).getStatusCode());
    }

    @Test(expected = NoContentException.class)
    public void should_throw_exception_when_there_is_no_brewery_base_on_brewery_id() throws NoContentException {
        when(service.getBreweryByBreweryId(ID)).thenThrow(NoContentException.class);
        controller.getBreweryByBreweryId(ID);
    }

    @Test
    public void should_return_status_created_when_controller_add_new_brewery() throws URISyntaxException {
        when(service.createNewBrewery(mapper.map(breweryDTO, Brewery.class))).thenReturn(brewery);
        when(mapper.map(brewery, BreweryDTO.class)).thenReturn(breweryDTO);
        assertEquals(HttpStatus.CREATED, controller.addNewBrewery(breweryDTO).getStatusCode());
    }

    @Test
    public void should_return_saved_brewery_which_equals_to_brewery_saved_by_controller() throws URISyntaxException {
        URI uri = new URI("add-beer" + brewery.getBreweryId());
        when(service.createNewBrewery(mapper.map(breweryDTO, Brewery.class))).thenReturn(brewery);
        when(mapper.map(brewery, BreweryDTO.class)).thenReturn(breweryDTO);
        assertEquals(ResponseEntity.created(uri).body(breweryDTO).getBody(), controller.addNewBrewery(breweryDTO).getBody());
    }

    @Test
    public void should_body_response_be_equal_to_response_from_controller() throws NoContentException {
        when(service.updateBreweryById(ID, mapper.map(breweryDTO, Brewery.class))).thenReturn(brewery);
        when(mapper.map(brewery, BreweryDTO.class)).thenReturn(breweryDTO);
        assertEquals(ResponseEntity.ok().body(breweryDTO), controller.updateBrewery(ID, breweryDTO));
    }

    @Test
    public void should_status_be_ok_and_be_equal_to_status_returned_by_controller() throws NoContentException {
        when(mapper.map(breweryDTO, Brewery.class)).thenReturn(brewery);
        when(service.updateBreweryById(ID, brewery)).thenReturn(brewery);
        assertEquals(HttpStatus.OK, controller.updateBrewery(ID, breweryDTO).getStatusCode());
    }

    @Test(expected = NoContentException.class)
    public void should_throw_exception_when_there_is_no_brewery_base_on_brewery_id_during_update() throws NoContentException {
        when(service.updateBreweryById(ID,mapper.map(breweryDTO, Brewery.class))).thenThrow(NoContentException.class);
        controller.updateBrewery(ID, breweryDTO);
    }

    @Test
    public void should_status_be_no_content_when_controller_deleted_brewery_by_brewery_id() throws NoContentException {
        assertEquals(HttpStatus.NO_CONTENT, controller.deleteBrewery(ID).getStatusCode());
    }

    @Test
    public void should_return_status_ok_when_controller_successfully_add_image_for_beer() throws IOException, NoContentException, InvalidImageParameters {
        assertEquals(ResponseEntity.status(HttpStatus.OK).body("File is uploaded successfully"),controller.uploadImage(ID,file));
    }

    @Test
    public void should_return_status_ok_when_controller_successfully_download_image_for_beer() throws NoContentException {
        when(service.getBreweryImageFromDbBaseOnBreweryId(ID)).thenReturn(newArray());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        assertEquals(ResponseEntity.ok().headers(headers).body(newArray()), controller.downloadImage(ID));
    }

    private byte [] newArray(){
        byte [] ds = new byte [10];
        return ds;
    }
}
