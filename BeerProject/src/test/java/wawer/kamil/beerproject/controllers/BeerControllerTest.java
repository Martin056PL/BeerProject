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
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.service.impl.BeerServiceImpl;
import wawer.kamil.beerproject.utils.mapper.BeerMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static wawer.kamil.beerproject.controllers.BeerTestHelper.*;


@ExtendWith(MockitoExtension.class)
class BeerControllerTest {

    @Mock
    BeerServiceImpl service;

    @Mock
    Pageable pageable;

    @Mock
    BeerMapper beerMapper;

    @Mock
    MultipartFile multipartFile;

    @InjectMocks
    BeerController beerController;

    private Page<Beer> beerPage;
    private Page<BeerResponse> beerResponsesPage;               //
    private List<Beer> listOfBeersBaseOnIDs;                   //
    private List<BeerResponse> listOfBeersResponses;            //   TODO zrobić update dla adnotacji @DisplayName
    private Beer beer;                                          //
    private BeerResponse beerResponse;                          //
    private BeerRequest beerRequest;                            //

    private static final Long beerID = 1L;
    private static final Long breweryID = 1L;

    @BeforeEach
    void setUp() {
        this.beerPage = getBeerPage();
        this.beerResponsesPage = getBeerResponsesPage();
        this.listOfBeersBaseOnIDs = getListOfBeers();
        this.listOfBeersResponses = getListOfBeersResponses();
        this.beer = getBeer();
        this.beerResponse = getBeerResponse();
        this.beerRequest = getBeerRequest();
    }

    //get

    @Test
    @DisplayName("Should return status ok when controller returns some beers page")
    void should_return_status_ok_when_controller_returns_some_beer_page() {
        // given
        when(service.findAllBeersPage(pageable)).thenReturn(beerPage);
        when(beerMapper.mapBeerEntityPageToBeerResponsePage(beerPage)).thenReturn(beerResponsesPage);

        //when
        ResponseEntity<Page<BeerResponse>> allBeersPage = beerController.findAllBeersPage(pageable);

        //then
        assertEquals(HttpStatus.OK, allBeersPage.getStatusCode());
        assertEquals(beerResponsesPage, allBeersPage.getBody());
    }

    @Test
    @DisplayName("Should return status ok when controller returns some beers list")
    void should_return_status_ok_when_controller_returns_some_beer_list() {
        //given
        when(service.findAllBeersList()).thenReturn(listOfBeersBaseOnIDs);
        when(beerMapper.mapListOfBeerEntityToListBeerResponse(listOfBeersBaseOnIDs)).thenReturn(listOfBeersResponses);

        //when
        ResponseEntity<List<BeerResponse>> allBeersList = beerController.findAllBeersList();

        //then
        assertEquals(HttpStatus.OK, allBeersList.getStatusCode());
        assertEquals(listOfBeersResponses, allBeersList.getBody());
    }

    @Test
    @DisplayName("Should return status ok when controller returns some beer page base on brewery id")
    void should_return_status_ok_when_controller_returns_some_beer_page_base_on_brewery_id() throws ElementNotFoundException {
        //given
        when(service.findAllBeersByBreweryIdPage(breweryID, pageable)).thenReturn(beerPage);
        when(beerMapper.mapBeerEntityPageToBeerResponsePage(beerPage)).thenReturn(beerResponsesPage);

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
        assertThrows(ElementNotFoundException.class, this::callGetBeersPageByBreweryIdWithException);
    }

    private void callGetBeersPageByBreweryIdWithException() throws ElementNotFoundException {
        //given
        when(service.findAllBeersByBreweryIdPage(breweryID, pageable)).thenThrow(ElementNotFoundException.class);

        //when
        beerController.findAllBeersByBreweryIdPage(breweryID, pageable);
    }

    @Test
    @DisplayName("Should return status ok when controller returns some beer page base on brewery id")
    void should_return_status_ok_when_controller_returns_some_beer_list_base_on_brewery_id() throws ElementNotFoundException {
        //given
        when(service.findAllBeersByBreweryIdList(breweryID)).thenReturn(listOfBeersBaseOnIDs);
        when(beerMapper.mapListOfBeerEntityToListBeerResponse(listOfBeersBaseOnIDs)).thenReturn(listOfBeersResponses);

        //when
        ResponseEntity<List<BeerResponse>> allBeersByBreweryIdPage = beerController.findAllBeersByBreweryIdList(breweryID);

        //then
        assertEquals(HttpStatus.OK, allBeersByBreweryIdPage.getStatusCode());
        assertEquals(listOfBeersResponses, allBeersByBreweryIdPage.getBody());
    }

    @Test
    @DisplayName("Should throw exception when there is no beer base on beer id")
    void should_throw_exception_when_there_is_no_beers_list_base_on_brewery_id() {
        //then
        assertThrows(ElementNotFoundException.class, this::callGetBeerListByBreweryIdWithException);
    }

    private void callGetBeerListByBreweryIdWithException() throws ElementNotFoundException {
        //given
        when(service.findAllBeersByBreweryIdList(breweryID)).thenThrow(ElementNotFoundException.class);

        //when
        beerController.findAllBeersByBreweryIdList(breweryID);
    }

    @Test
    @DisplayName("Should return ")
    void should() throws ElementNotFoundException {
        //given
        when(service.findBeerById(breweryID)).thenReturn(beer);
        when(beerMapper.mapBeerToBeerResponse(beer)).thenReturn(beerResponse);

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
        assertThrows(ElementNotFoundException.class, this::callGetBeerByIdWithException);
    }

    private void callGetBeerByIdWithException() throws ElementNotFoundException {
        //given
        when(service.findBeerById(beerID)).thenThrow(ElementNotFoundException.class);

        //when
        beerController.findProperBeerByBeerId(beerID);
    }


    //post

    @Test
    @DisplayName("Should throw exception when there is no beer base on beer id")
    void should_return_response_body_equal_to_controller_response_with_just_created_beer_base_on_request_body_beer() throws URISyntaxException, ElementNotFoundException {
        //given
        when(beerMapper.mapBeerRequestToBeerEntity(beerRequest)).thenReturn(beer);
        when(service.addNewBeerAssignedToBreweryByBreweryId(breweryID, beer)).thenReturn(beer);
        when(beerMapper.mapBeerToBeerResponse(beer)).thenReturn(beerResponse);

        //when
        ResponseEntity<BeerResponse> beerResponseResponseEntity = beerController.addBeerToBreweryByBreweryId(breweryID, beerRequest);

        //then
        assertEquals(HttpStatus.CREATED, beerResponseResponseEntity.getStatusCode());
        assertEquals(beerResponse, beerResponseResponseEntity.getBody());
    }

    @Test
    @DisplayName("Should throw exception when there is no beer base on beer id")
    void should_throw_exception_when_there_is_no_beer_base_on_beer_id_when_creating_beer() {
        //then
        assertThrows(ElementNotFoundException.class, this::callCreateBeerWithBreweryIdWithException);
    }

    private void callCreateBeerWithBreweryIdWithException() throws ElementNotFoundException, URISyntaxException {
        //given
        when(beerMapper.mapBeerRequestToBeerEntity(beerRequest)).thenReturn(beer);
        when(service.addNewBeerAssignedToBreweryByBreweryId(beerID, beer)).thenThrow(ElementNotFoundException.class);

        //when
        beerController.addBeerToBreweryByBreweryId(breweryID, beerRequest);
    }

    //put

    @Test
    void should_return_response_body_equal_to_controller_response_with_just_updated_beer_base_on_request_body_beer_and_beer_id() throws ElementNotFoundException {
        //given
        when(beerMapper.mapBeerRequestToBeerEntity(beerRequest)).thenReturn(beer);
        when(service.updateBeerByBeerId(beerID, beer)).thenReturn(beer);
        when(beerMapper.mapBeerToBeerResponse(beer)).thenReturn(beerResponse);

        //when
        ResponseEntity<BeerResponse> beerResponseResponseEntity = beerController.updateBeerBeerId(beerID, beerRequest);

        //then
        assertEquals(HttpStatus.OK, beerResponseResponseEntity.getStatusCode());
        assertEquals(beerResponse, beerResponseResponseEntity.getBody());
    }

    @Test
    @DisplayName("Should throw exception when there is no beer base on beer id")
    void should_throw_exception_when_there_is_no_beer_base_on_beer_id_when_creating_beer123() {
        //then
        assertThrows(ElementNotFoundException.class, this::callUpdateBeerWithBeerIdWithException);
    }

    private void callUpdateBeerWithBeerIdWithException() throws ElementNotFoundException {
        //given
        when(beerMapper.mapBeerRequestToBeerEntity(beerRequest)).thenReturn(beer);
        when(service.updateBeerByBeerId(beerID, beer)).thenThrow(ElementNotFoundException.class);

        //when
        beerController.updateBeerBeerId(breweryID, beerRequest);
    }

    @Test
    void should_return_response_body_equal_to_controller_response_with_just_updated_beer_base_on_request_body_beer_and_beer_idsdfsdfdsf() throws ElementNotFoundException {
        //given
        when(beerMapper.mapBeerRequestToBeerEntity(beerRequest)).thenReturn(beer);
        when(service.updateBeerByBreweryIdAndBeerId(breweryID, beerID, beer)).thenReturn(beer);
        when(beerMapper.mapBeerToBeerResponse(beer)).thenReturn(beerResponse);

        //when
        ResponseEntity<BeerResponse> beerResponseResponseEntity = beerController.updateBeerBaseOnBreweryIdAndBeerId(breweryID, beerID, beerRequest);

        //then
        assertEquals(HttpStatus.OK, beerResponseResponseEntity.getStatusCode());
        assertEquals(beerResponse, beerResponseResponseEntity.getBody());
    }

    @Test
    @DisplayName("Should throw exception when there is no beer base on beer id")
    void should_throw_exception_when_there_is_no_beer_base_on_beer_id_when_creating_beerddd() {
        //then
        assertThrows(ElementNotFoundException.class, this::callUpdateBeerWithBeerIdWithException123);
    }

    private void callUpdateBeerWithBeerIdWithException123() throws ElementNotFoundException {
        //given
        when(beerMapper.mapBeerRequestToBeerEntity(beerRequest)).thenReturn(beer);
        when(service.updateBeerByBreweryIdAndBeerId(breweryID, beerID, beer)).thenThrow(ElementNotFoundException.class);

        //when
        beerController.updateBeerBaseOnBreweryIdAndBeerId(breweryID, beerID, beerRequest);
    }

    //delete

    @Test
    @DisplayName("Should throw exception when there is no beer base on beer id")
    void should_return_status_no_content_when_controller_successfully_delete_beer_base_on_beer_id() throws ElementNotFoundException {
        assertEquals(ResponseEntity.noContent().build(), beerController.deleteBeerByBeerId(beerID));
    }

    @Test
    @DisplayName("")
    void should_throw_exception_when_there_is_no_beer_base_on_beer_id_when_creating_beer1() {
        //then
        assertThrows(ElementNotFoundException.class, this::callDeleteBeerWithBeerIdWithException);
    }

    private void callDeleteBeerWithBeerIdWithException() throws ElementNotFoundException {
        //given
        doThrow(new ElementNotFoundException()).when(service).deleteBeerById(beerID);

        //when
        beerController.deleteBeerByBeerId(beerID);
    }

    @Test
    @DisplayName("Should throw exception when there is no beer base on beer id")
    void should_return_status_ok_when_controller_successfully_add_image_for_brewery() throws IOException, ElementNotFoundException, InvalidImageParameters {
        //given
        ResponseEntity<String> file_is_uploaded_successfully = ResponseEntity.status(HttpStatus.OK).body("File is uploaded successfully");

        //when
        ResponseEntity<Object> responseEntity = beerController.uploadImage(beerID, multipartFile);

        //then
        assertEquals(file_is_uploaded_successfully, responseEntity);
    }

    @Test
    @DisplayName("Should throw exception when there is no beer base on beer id")
    void should_return_status_ok_when_controller_successfully_download_image_for_brewery() throws ElementNotFoundException {
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
