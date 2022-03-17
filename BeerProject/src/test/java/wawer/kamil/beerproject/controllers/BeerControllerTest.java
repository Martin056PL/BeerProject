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
import wawer.kamil.beerproject.dto.request.BeerRequest;
import wawer.kamil.beerproject.dto.response.BeerResponse;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.service.impl.BeerServiceImpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static wawer.kamil.beerproject.helpers.BeerTestHelper.*;


@ExtendWith(MockitoExtension.class)
class BeerControllerTest {

    @Mock
    BeerServiceImpl service;

    @Mock
    Pageable pageable;

    @Mock
    MultipartFile multipartFile;

    @InjectMocks
    BeerController beerController;

    private Page<BeerResponse> beerResponsesPage;
    private List<BeerResponse> listOfBeersResponses;
    private BeerResponse beerResponse;
    private BeerRequest beerRequest;

    private static final Long beerID = 1L;
    private static final Long breweryID = 1L;

    @BeforeEach
    void setUp() {
        this.beerResponsesPage = getBeerResponsesPage();
        this.listOfBeersResponses = getListOfBeersResponses();
        this.beerResponse = getBeerResponse();
        this.beerRequest = getBeerRequest();
    }

    //get

    @Test
    @DisplayName("Should return status ok with response body when controller returns beers page")
    void should_return_status_ok_with_response_body_when_controller_returns_beer_page() {
        // given
        when(service.findAllBeersPage(pageable)).thenReturn(beerResponsesPage);

        //when
        ResponseEntity<Page<BeerResponse>> allBeersPage = beerController.findAllBeersPage(pageable);

        //then
        assertEquals(HttpStatus.OK, allBeersPage.getStatusCode());
        assertEquals(beerResponsesPage, allBeersPage.getBody());
    }

    @Test
    @DisplayName("Should return status ok with response body when controller returns some beers list")
    void should_return_status_ok_with_response_body_when_controller_returns_some_beer_list() {
        //given
        when(service.findAllBeersList()).thenReturn(listOfBeersResponses);

        //when
        ResponseEntity<List<BeerResponse>> allBeersList = beerController.findAllBeersList();

        //then
        assertEquals(HttpStatus.OK, allBeersList.getStatusCode());
        assertEquals(listOfBeersResponses, allBeersList.getBody());
    }

    @Test
    @DisplayName("Should return status ok with response body when controller returns some beer page base on brewery id")
    void should_return_status_ok_with_response_body_when_controller_returns_some_beer_page_base_on_brewery_id() throws ElementNotFoundException {
        //given
        when(service.findAllBeersByBreweryIdPage(breweryID, pageable)).thenReturn(beerResponsesPage);

        //when
        ResponseEntity<Page<BeerResponse>> allBeersByBreweryIdPage = beerController.findAllBeersByBreweryIdPage(breweryID, pageable);

        //then
        assertEquals(HttpStatus.OK, allBeersByBreweryIdPage.getStatusCode());
        assertEquals(beerResponsesPage, allBeersByBreweryIdPage.getBody());
    }

    @Test
    @DisplayName("Should throw exception when there is no beer base on beer id")
    void should_throw_exception_when_there_is_no_beers_page_base_on_brewery_id() {
        //then
        assertThrows(ElementNotFoundException.class, this::callFindAllBeersByBreweryIdPageWithException);
    }

    private void callFindAllBeersByBreweryIdPageWithException() throws ElementNotFoundException {
        //given
        when(service.findAllBeersByBreweryIdPage(breweryID, pageable)).thenThrow(ElementNotFoundException.class);

        //when
        beerController.findAllBeersByBreweryIdPage(breweryID, pageable);
    }

    @Test
    @DisplayName("Should return status ok with response body when controller returns some beer page base on brewery id")
    void should_return_status_ok_with_response_body_when_controller_returns_some_beer_list_base_on_brewery_id() throws ElementNotFoundException {
        //given
        when(service.findAllBeersByBreweryIdList(breweryID)).thenReturn(listOfBeersResponses);

        //when
        ResponseEntity<List<BeerResponse>> allBeersByBreweryIdPage = beerController.findAllBeersByBreweryIdList(breweryID);

        //then
        assertEquals(HttpStatus.OK, allBeersByBreweryIdPage.getStatusCode());
        assertEquals(listOfBeersResponses, allBeersByBreweryIdPage.getBody());
    }

    @Test
    @DisplayName("Should throw exception when there is no brewery id during finding all beers base on brewery id")
    void should_throw_exception_when_there_is_no_brewery_id_during_finding_all_beers_base_on_brewery_id() {
        //then
        assertThrows(ElementNotFoundException.class, this::callFindAllBeersByBreweryIdListWithException);
    }

    private void callFindAllBeersByBreweryIdListWithException() throws ElementNotFoundException {
        //given
        when(service.findAllBeersByBreweryIdList(breweryID)).thenThrow(ElementNotFoundException.class);

        //when
        beerController.findAllBeersByBreweryIdList(breweryID);
    }

    @Test
    @DisplayName("Should return status ok with response body when controller returns some single beer base on beer id")
    void should_return_status_ok_with_response_body_when_controller_returns_some_single_beer_base_on_beer_id() throws ElementNotFoundException {
        //given
        when(service.findBeerById(breweryID)).thenReturn(beerResponse);

        //when
        ResponseEntity<BeerResponse> properBeerByBeerId = beerController.findProperBeerByBeerId(beerID);

        //then
        assertEquals(HttpStatus.OK, properBeerByBeerId.getStatusCode());
        assertEquals(beerResponse, properBeerByBeerId.getBody());
    }

    @Test
    @DisplayName("Should throw exception when there is no beer base on beer id")
    void should_throw_exception_when_there_is_no_beer_base_on_beer_id() {
        //then
        assertThrows(ElementNotFoundException.class, this::callFindProperBeerByBeerIdWithException);
    }

    private void callFindProperBeerByBeerIdWithException() throws ElementNotFoundException {
        //given
        when(service.findBeerById(beerID)).thenThrow(ElementNotFoundException.class);

        //when
        beerController.findProperBeerByBeerId(beerID);
    }


    //post

    @Test
    @DisplayName("Should return status created with response body when controller returns just created beer")
    void should_return_status_created_with_response_body_when_controller_returns_just_created_beer() throws URISyntaxException, ElementNotFoundException {
        //given
        when(service.addNewBeerAssignedToBreweryByBreweryId(breweryID, beerRequest)).thenReturn(beerResponse);

        //when
        ResponseEntity<BeerResponse> beerResponseResponseEntity = beerController.addBeerToBreweryByBreweryId(breweryID, beerRequest);

        //then
        assertEquals(HttpStatus.CREATED, beerResponseResponseEntity.getStatusCode());
        assertEquals(beerResponse, beerResponseResponseEntity.getBody());
    }

    @Test
    @DisplayName("Should throw exception when there is no brewery base on brewery id during adding new beer")
    void should_throw_exception_when_there_is_no_brewery_base_on_brewery_id_during_adding_new_beer() {
        //then
        assertThrows(ElementNotFoundException.class, this::callAddBeerToBreweryByBreweryIdWithException);
    }

    private void callAddBeerToBreweryByBreweryIdWithException() throws ElementNotFoundException, URISyntaxException {
        //given
        when(service.addNewBeerAssignedToBreweryByBreweryId(beerID, beerRequest)).thenThrow(ElementNotFoundException.class);

        //when
        beerController.addBeerToBreweryByBreweryId(breweryID, beerRequest);
    }

    //put

    @Test
    @DisplayName("Should return status ok with response body when controller returns just updated beer base on beer id and new version of beer")
    void should_return_status_ok_with_response_body_when_controller_returns_just_updated_beer_base_on_beer_id_and_new_version_of_beer() throws ElementNotFoundException {
        //given
        when(service.updateBeerByBeerId(beerID, beerRequest)).thenReturn(beerResponse);

        //when
        ResponseEntity<BeerResponse> beerResponseResponseEntity = beerController.updateBeerBeerId(beerID, beerRequest);

        //then
        assertEquals(HttpStatus.OK, beerResponseResponseEntity.getStatusCode());
        assertEquals(beerResponse, beerResponseResponseEntity.getBody());
    }

    @Test
    @DisplayName("Should throw exception when there is no beer base on beer id when updating beer")
    void should_throw_exception_when_there_is_no_beer_base_on_beer_id_when_updating_beer() {
        //then
        assertThrows(ElementNotFoundException.class, this::callUpdateBeerBeerIdWithException);
    }

    private void callUpdateBeerBeerIdWithException() throws ElementNotFoundException {
        //given
        when(service.updateBeerByBeerId(beerID, beerRequest)).thenThrow(ElementNotFoundException.class);

        //when
        beerController.updateBeerBeerId(breweryID, beerRequest);
    }

    @Test
    @DisplayName("should return status ok with response body when controller returns just updated beer base on existing brewery id and beer id")
    void should_return_status_ok_with_response_body_when_controller_returns_just_updated_beer_base_on_existing_brewery_id_and_beer_id() throws ElementNotFoundException {
        //given
        when(service.updateBeerByBreweryIdAndBeerId(breweryID, beerID, beerRequest)).thenReturn(beerResponse);

        //when
        ResponseEntity<BeerResponse> beerResponseResponseEntity = beerController.updateBeerBaseOnBreweryIdAndBeerId(breweryID, beerID, beerRequest);

        //then
        assertEquals(HttpStatus.OK, beerResponseResponseEntity.getStatusCode());
        assertEquals(beerResponse, beerResponseResponseEntity.getBody());
    }

    @Test
    @DisplayName("Should throw exception when there is no beer or brewery base on ids when updating beer")
    void should_throw_exception_when_there_is_no_beer_or_brewery_base_on_ids_when_updating_beer() {
        //then
        assertThrows(ElementNotFoundException.class, this::callUpdateBeerBaseOnBreweryIdAndBeerId);
    }

    private void callUpdateBeerBaseOnBreweryIdAndBeerId() throws ElementNotFoundException {
        //given
        when(service.updateBeerByBreweryIdAndBeerId(breweryID, beerID, beerRequest)).thenThrow(ElementNotFoundException.class);

        //when
        beerController.updateBeerBaseOnBreweryIdAndBeerId(breweryID, beerID, beerRequest);
    }

    //delete

    @Test
    @DisplayName("Should return status no content when deleting beer base on beer id")
    void Should_return_status_no_content_when_deleting_beer_base_on_beer_id() throws ElementNotFoundException {
        assertEquals(ResponseEntity.noContent().build(), beerController.deleteBeerByBeerId(beerID));
    }

    @Test
    @DisplayName("Should throw exception when there is no beer base on beer id when deleting beer")
    void should_throw_exception_when_there_is_no_beer_base_on_beer_id_when_deleting_beer() {
        //then
        assertThrows(ElementNotFoundException.class, this::callDeleteBeerByBeerIdIdWithException);
    }

    private void callDeleteBeerByBeerIdIdWithException() throws ElementNotFoundException {
        //given
        doThrow(new ElementNotFoundException()).when(service).deleteBeerById(beerID);

        //when
        beerController.deleteBeerByBeerId(beerID);
    }

    @Test
    @DisplayName("Should return status ok and response message when controller successfully add image for brewery")
    void should_return_status_ok_and_response_message_when_controller_successfully_add_image_for_brewery() throws IOException, ElementNotFoundException, InvalidImageParameters {
        //given
        ResponseEntity<String> file_is_uploaded_successfully = ResponseEntity.status(HttpStatus.OK).body("File is uploaded successfully");

        //when
        ResponseEntity<Object> responseEntity = beerController.uploadImage(beerID, multipartFile);

        //then
        assertEquals(file_is_uploaded_successfully, responseEntity);
    }

    @Test
    @DisplayName("Should return status ok when controller successfully getting image for beer")
    void should_return_status_ok_when_controller_successfully_getting_image_for_beer() throws ElementNotFoundException {
        //given
        when(service.getBeerImageBaseOnBeerId(beerID)).thenReturn(newArrayForBeerImage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        //when
        ResponseEntity<byte[]> actual = beerController.downloadImage(beerID);

        //then
        assertEquals(ResponseEntity.ok().headers(headers).body(newArrayForBeerImage()), actual);
    }
}
