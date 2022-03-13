package wawer.kamil.beerproject.controllers;

import org.junit.jupiter.api.BeforeEach;
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
import static wawer.kamil.beerproject.controllers.BreweryTestHelper.*;

@ExtendWith(MockitoExtension.class)
class BreweryControllerTest {

    @Mock
    BreweryServiceImpl service;

    @Mock
    BreweryMapper mapper;

    @Mock
    Pageable pageable;

    @Mock
    MultipartFile multipartFile;

    @InjectMocks
    BreweryController controller;

    private Page<Brewery> breweryPage;
    private Page<BreweryResponse> breweryResponsePage;
    private List<Brewery> breweryList;
    private List<BreweryResponse> breweryResponseList;
    private BreweryRequest breweryRequest;
    private BreweryRequest updatedSingleBreweryRequest;
    private Brewery singleBreweryBeforeSave;
    private Brewery singleSavedBrewery;
    private Brewery updatedSingleBreweryBeforeSave;
    private Brewery updatedSingleBreweryAfterSave;
    private BreweryResponse updatedSingleBreweryResponseAfterSave;
    private BreweryResponse singleBreweryResponse;

    private final static Long ID = 1L;
    private final static String URI = "add-beer/";

    @BeforeEach
    void setUp() {
        this.breweryPage = getBreweryPage();
        this.breweryResponsePage = getBreweryResponsePage();
        this.breweryList = getBreweryList();
        this.breweryResponseList = getBreweryResponseList();
        this.breweryRequest = getSingleBreweryRequest();
        this.updatedSingleBreweryRequest = getUpdatedSingleBreweryRequest();
        this.singleBreweryBeforeSave = getSingleBreweryBeforeSave();
        this.singleSavedBrewery = getSingleSavedBrewery();
        this.updatedSingleBreweryBeforeSave = getUpdatedSingleBreweryBeforeSave();
        this.updatedSingleBreweryAfterSave = getUpdatedSingleBreweryAfterSave();
        this.updatedSingleBreweryResponseAfterSave = getUpdatedSingleBreweryResponseAfterSave();
        this.singleBreweryResponse = getSingleBreweryResponse();
    }

    @Test
    @DisplayName("Should return response entity equal to controllers response entity for brewery PAGE")
    void should_return_response_entity_equal_to_controllers_response_entity_for_brewery_page() {
        //given
        when(service.getAllBreweryPage(pageable)).thenReturn(breweryPage);
        when(mapper.mapBreweryEntityPageToBreweryResponsePage(breweryPage)).thenReturn(breweryResponsePage);

        //when
        ResponseEntity<Page<BreweryResponse>> allBreweryPage = controller.getAllBreweryPage(pageable);

        //then
        assertEquals(HttpStatus.OK, allBreweryPage.getStatusCode());
        assertEquals(breweryResponsePage, allBreweryPage.getBody());
    }

    @Test
    @DisplayName("Should return response entity equal to controllers response entity for brewery LIST")
    void should_return_response_entity_equal_to_controllers_response_entity_for_brewery_list() {
        //given
        when(service.getAllBreweryList()).thenReturn(breweryList);
        when(mapper.mapListOfBreweryEntityToListBreweryResponse(breweryList)).thenReturn(breweryResponseList);

        //when
        ResponseEntity<List<BreweryResponse>> allBreweryList = controller.getAllBreweryList();

        //then
        assertEquals(HttpStatus.OK, allBreweryList.getStatusCode());
        assertEquals(breweryResponseList, allBreweryList.getBody());
    }

    @Test
    @DisplayName("Should return response entity equal to controllers response entity base on SINGLE brewery id")
    void should_return_response_entity_equal_to_controllers_response_entity_base_on_brewery_id() throws ElementNotFoundException {
        //given
        when(service.findBreweryById(ID)).thenReturn(singleSavedBrewery);
        when(mapper.mapBreweryToBreweryResponse(singleSavedBrewery)).thenReturn(singleBreweryResponse);

        //when
        ResponseEntity<BreweryResponse> breweryById = controller.getBreweryById(ID);

        //then
        assertEquals(HttpStatus.OK, breweryById.getStatusCode());
        assertEquals(ok(singleBreweryResponse).getBody(), breweryById.getBody());
    }

    @Test
    @DisplayName("Should throw exception when there is no brewery base on brewery id")
    void should_throw_exception_when_there_is_no_brewery_base_on_brewery_id() {
        assertThrows(ElementNotFoundException.class, this::callGetBreweryByIdWithException);
    }

    private void callGetBreweryByIdWithException() throws ElementNotFoundException {
        when(service.findBreweryById(ID)).thenThrow(ElementNotFoundException.class);
        controller.getBreweryById(ID);
    }

    @Test
    @DisplayName("Should return response entity equal to controllers response entity base on CREATED brewery")
    void should_return_response_entity_equal_to_controllers_response_entity_base_on_created_brewery() throws URISyntaxException {
        //given
        when(mapper.mapBreweryRequestToBreweryEntity(breweryRequest)).thenReturn(singleBreweryBeforeSave);
        when(service.createNewBrewery(singleBreweryBeforeSave)).thenReturn(singleSavedBrewery);
        when(mapper.mapBreweryToBreweryResponse(singleSavedBrewery)).thenReturn(singleBreweryResponse);

        //when
        ResponseEntity<BreweryResponse> breweryById = controller.addNewBrewery(breweryRequest);

        //then
        assertEquals(HttpStatus.CREATED, breweryById.getStatusCode());
        assertEquals(created(new URI(URI + singleBreweryResponse.getId())).body(singleBreweryResponse).getBody(), breweryById.getBody());
    }


    @Test
    @DisplayName("Should return response entity equal to controllers response entity base on UPDATED brewery")
    void should_return_response_entity_equal_to_controllers_response_entity_base_on_UPDATED_brewery() throws ElementNotFoundException {
        // given
        when(mapper.mapBreweryRequestToBreweryEntity(breweryRequest)).thenReturn(updatedSingleBreweryBeforeSave);
        when(service.updateBreweryById(ID, updatedSingleBreweryBeforeSave)).thenReturn(updatedSingleBreweryAfterSave);
        when(mapper.mapBreweryToBreweryResponse(updatedSingleBreweryAfterSave)).thenReturn(updatedSingleBreweryResponseAfterSave);

        //when
        ResponseEntity<BreweryResponse> breweryResponseResponseEntity = controller.updateBrewery(ID, breweryRequest);

        //then
        assertEquals(HttpStatus.OK, breweryResponseResponseEntity.getStatusCode());
        assertEquals(ok(updatedSingleBreweryResponseAfterSave).getBody(), breweryResponseResponseEntity.getBody());
    }

    @Test
    @DisplayName("Should throw exception when there is no brewery base on brewery id during update")
    void should_throw_exception_when_there_is_no_brewery_base_on_brewery_id_during_update() {
        //then
        assertThrows(ElementNotFoundException.class, this::callUpdateBreweryByIdWithException);
    }

    private void callUpdateBreweryByIdWithException() throws ElementNotFoundException {
        //given
        when(mapper.mapBreweryRequestToBreweryEntity(updatedSingleBreweryRequest)).thenReturn(updatedSingleBreweryBeforeSave);
        when(service.updateBreweryById(ID, updatedSingleBreweryBeforeSave)).thenThrow(ElementNotFoundException.class);

        //when
        controller.updateBrewery(ID, updatedSingleBreweryRequest);
    }


    @Test
    @DisplayName("Should return response entity equal to controllers response entity base on DELETE brewery")
    void should_return_response_entity_equal_to_controllers_response_entity_base_on_DELETE_brewery() throws ElementNotFoundException {
        //when
        ResponseEntity actual = controller.deleteBrewery(ID);

        //then
        assertEquals(noContent().build(), actual);
        assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
    }

    @Test
    @DisplayName("Should throw exception when there is no brewery base on brewery id during delete")
    void should_throw_exception_when_there_is_no_brewery_base_on_brewery_id_during_delete() {
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
    void should_return_status_ok_when_controller_successfully_add_image_for_beer() throws IOException, ElementNotFoundException, InvalidImageParameters {
        //when
        ResponseEntity<Object> getImageResponseEntity = controller.uploadImage(ID, multipartFile);

        //then
        assertEquals(HttpStatus.OK, controller.uploadImage(ID, multipartFile).getStatusCode());
        assertEquals("File is uploaded successfully", controller.uploadImage(ID, multipartFile).getBody());
    }

    @Test
    @DisplayName("Should return status ok when controller successfully download image for beer")
    void should_return_status_ok_when_controller_successfully_download_image_for_beer() throws ElementNotFoundException {
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
