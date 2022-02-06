package wawer.kamil.beerproject.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.dto.request.BreweryRequest;
import wawer.kamil.beerproject.dto.response.BreweryResponse;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.model.Brewery;
import wawer.kamil.beerproject.service.impl.BreweryServiceImpl;
import wawer.kamil.beerproject.utils.mapper.BreweryMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.ResponseEntity.*;
import static wawer.kamil.beerproject.controllers.BreweryControllerTestHelper.*;

@ExtendWith(MockitoExtension.class)
public class BreweryControllerTest {

    @Mock
    BreweryServiceImpl service;

    @Mock
    Pageable pageable;

    @Mock
    BreweryMapper mapper;

    @Mock
    MultipartFile multipartFile;

    @InjectMocks
    BreweryController controller;

    private final static Long ID = 1L;
    private final static String URI = "add-beer/";


    @Test
    @DisplayName("Should return response entity equal to controllers response entity for brewery PAGE")
    public void should_return_response_entity_equal_to_controllers_response_entity_for_brewery_page() {
        //given
        Page<Brewery> breweryPage = getBreweryPage();
        when(service.getAllBreweryPage(pageable)).thenReturn(getBreweryPage());
        when(mapper.mapBreweryEntityPageToBreweryResponsePage(breweryPage)).thenReturn(getBreweryResponsePage());

        //when
        ResponseEntity<Page<BreweryResponse>> allBreweryPage = controller.getAllBreweryPage(pageable);

        //then
        assertEquals(ok(getBreweryResponsePage()), allBreweryPage);
        assertEquals(HttpStatus.OK, allBreweryPage.getStatusCode());
        assertEquals(getBreweryResponsePage(), allBreweryPage.getBody());
    }

    @Test
    @DisplayName("Should return response entity equal to controllers response entity for brewery LIST")
    public void should_return_response_entity_equal_to_controllers_response_entity_for_brewery_list() {
        //given
        List<Brewery> breweryList = getBreweryList();
        when(service.getAllBreweryList()).thenReturn(getBreweryList());
        when(mapper.mapListOfBreweryEntityToListBreweryResponse(breweryList)).thenReturn(getBreweryResponseList());

        //when
        ResponseEntity<List<BreweryResponse>> allBreweryList = controller.getAllBreweryList();

        //then
        assertEquals(ok(getBreweryResponseList()), allBreweryList);
        assertEquals(HttpStatus.OK, allBreweryList.getStatusCode());
        assertEquals(getBreweryResponseList(), allBreweryList.getBody());
    }

    @Test
    @DisplayName("Should return response entity equal to controllers response entity base on SINGLE brewery id")
    public void should_return_response_entity_equal_to_controllers_response_entity_base_on_brewery_id() throws ElementNotFoundException {
        //given
        Brewery b = getSingleSavedBrewery();
        when(service.getBreweryById(ID)).thenReturn(b);
        when(mapper.mapBreweryToBreweryResponse(b)).thenReturn(getSingleBreweryResponse());

        //when
        ResponseEntity<BreweryResponse> breweryById = controller.getBreweryById(ID);

        //then
        assertEquals(ok(getSingleBreweryResponse()), breweryById);
        assertEquals(HttpStatus.OK, breweryById.getStatusCode());
        assertEquals(ok(getSingleBreweryResponse()).getBody(), breweryById.getBody());
    }

    @Test
    @DisplayName("Should throw exception when there is no brewery base on brewery id")
    public void should_throw_exception_when_there_is_no_brewery_base_on_brewery_id() {
        assertThrows(ElementNotFoundException.class, this::callGetBreweryByIdWithException);
    }

    private void callGetBreweryByIdWithException() throws ElementNotFoundException {
        when(service.getBreweryById(ID)).thenThrow(ElementNotFoundException.class);
        controller.getBreweryById(ID);
    }

    @Test
    @DisplayName("Should return response entity equal to controllers response entity base on CREATED brewery")
    public void should_return_response_entity_equal_to_controllers_response_entity_base_on_created_brewery() throws URISyntaxException {
        //given
        BreweryRequest brq = getSingleBreweryRequest();
        when(mapper.mapBreweryRequestToBreweryEntity(brq)).thenReturn(getSingleBreweryBeforeSave());
        when(service.createNewBrewery(getSingleBreweryBeforeSave())).thenReturn(getSingleSavedBrewery());
        when(mapper.mapBreweryToBreweryResponse(getSingleSavedBrewery())).thenReturn(getSingleBreweryResponse());

        //when
        ResponseEntity<BreweryResponse> breweryById = controller.addNewBrewery(brq);

        //then
        assertEquals(created(new URI(URI + getSingleBreweryResponse().getId())).body(getSingleBreweryResponse()), breweryById);
        assertEquals(HttpStatus.CREATED, breweryById.getStatusCode());
        assertEquals(created(new URI(URI + getSingleBreweryResponse().getId())).body(getSingleBreweryResponse()).getBody(), breweryById.getBody());
    }


    @Test
    @DisplayName("Should return response entity equal to controllers response entity base on UPDATED brewery")
    public void should_return_response_entity_equal_to_controllers_response_entity_base_on_UPDATED_brewery() throws ElementNotFoundException {
        // given
        BreweryRequest brq = getUpdatedSingleBreweryRequest();
        when(mapper.mapBreweryRequestToBreweryEntity(brq)).thenReturn(getUpdatedSingleBreweryBeforeSave());
        when(service.updateBreweryById(ID, getUpdatedSingleBreweryBeforeSave())).thenReturn(getUpdatedSingleBreweryAfterSave());
        when(mapper.mapBreweryToBreweryResponse(getUpdatedSingleBreweryAfterSave())).thenReturn(getUpdatedSingleBreweryResponseAfterSave());

        //when
        ResponseEntity<BreweryResponse> breweryResponseResponseEntity = controller.updateBrewery(ID, getUpdatedSingleBreweryRequest());

        //then
        assertEquals(ok(getUpdatedSingleBreweryResponseAfterSave()), breweryResponseResponseEntity);
        assertEquals(HttpStatus.OK, breweryResponseResponseEntity.getStatusCode());
        assertEquals(ok(getUpdatedSingleBreweryResponseAfterSave()).getBody(), breweryResponseResponseEntity.getBody());
    }

    @Test
    @DisplayName("Should throw exception when there is no brewery base on brewery id during update")
    public void should_throw_exception_when_there_is_no_brewery_base_on_brewery_id_during_update() {
        //then
        assertThrows(ElementNotFoundException.class, this::callUpdateBreweryByIdWithException);
    }

    private void callUpdateBreweryByIdWithException() throws ElementNotFoundException {
        //given
        when(mapper.mapBreweryRequestToBreweryEntity(getUpdatedSingleBreweryRequest())).thenReturn(getUpdatedSingleBreweryBeforeSave());
        when(service.updateBreweryById(ID, getUpdatedSingleBreweryBeforeSave())).thenThrow(ElementNotFoundException.class);

        //when
        controller.updateBrewery(ID, getUpdatedSingleBreweryRequest());
    }


    @Test
    @DisplayName("Should return response entity equal to controllers response entity base on DELETE brewery")
    public void should_return_response_entity_equal_to_controllers_response_entity_base_on_DELETE_brewery() throws ElementNotFoundException {
        //when
        ResponseEntity actual = controller.deleteBrewery(ID);

        //then
        assertEquals(noContent().build(), actual);
        assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
    }

    @Test
    @DisplayName("Should throw exception when there is no brewery base on brewery id during delete")
    public void should_throw_exception_when_there_is_no_brewery_base_on_brewery_id_during_delete() {
        //then
        assertThrows(ElementNotFoundException.class, this::callDeleteBreweryByIdWithException);
    }

    private void callDeleteBreweryByIdWithException() throws ElementNotFoundException {
        //when
        doThrow(new ElementNotFoundException()).when(service).deleteBreweryById(ID);
        controller.deleteBrewery(ID);
    }

    @Test
    @DisplayName("Should return status ok when controller successfully add image for beer")
    public void should_return_status_ok_when_controller_successfully_add_image_for_beer() throws IOException, ElementNotFoundException, InvalidImageParameters {
        //when
        ResponseEntity<Object> getImageResponseEntity = controller.uploadImage(ID, multipartFile);

        //then
        assertEquals(ResponseEntity.status(HttpStatus.OK).body("File is uploaded successfully"), getImageResponseEntity);
        assertEquals(HttpStatus.OK, controller.uploadImage(ID, multipartFile).getStatusCode());
        assertEquals("File is uploaded successfully", controller.uploadImage(ID, multipartFile).getBody());
    }

    @Test
    @DisplayName("Should return status ok when controller successfully download image for beer")
    public void should_return_status_ok_when_controller_successfully_download_image_for_beer() throws ElementNotFoundException {
        //given
        when(service.getBreweryImageFromDbBaseOnBreweryId(ID)).thenReturn(newArray());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        //when
        ResponseEntity<Object> savedImageResponseEntity = controller.downloadImage(ID);

        //then
        assertEquals(ResponseEntity.ok().headers(headers).body(newArray()), savedImageResponseEntity);
    }
}
