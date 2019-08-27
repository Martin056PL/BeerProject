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
import wawer.kamil.beerproject.domain.Beer;
import wawer.kamil.beerproject.dto.BeerDTO;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.service.BeerServiceImpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BeerControllerTest {

    @Mock
    BeerServiceImpl service;

    @Mock
    Pageable pageable;

    @Mock
    Page<Beer> page;

    @Mock
    List<Beer> list;

    @Mock
    Beer beer;

    @Mock
    BeerDTO beerDTO;

    @Mock
    ModelMapper mapper;

    @Mock
    MultipartFile file;

    @InjectMocks
    BeerController beerController;

    private static final Long beerID = 1L;
    private static final Long breweryID = 1L;

    //get all

    @Test
    public void should_return_response_body_equal_to_controller_response_with_some_beer_page() {
        when(service.findAllBeersPage(pageable)).thenReturn(page);
        assertEquals(ResponseEntity.ok().body(page).getBody(), beerController.findAllBeersPage(pageable).getBody());
    }

    @Test
    public void should_return_status_ok_when_controller_returns_some_beer_page() {
        when(service.findAllBeersPage(pageable)).thenReturn(page);
        assertEquals(HttpStatus.OK, beerController.findAllBeersPage(pageable).getStatusCode());
    }

    @Test
    public void should_return_response_body_equal_to_controller_response_with_some_beer_list(){
        when(service.findAllBeersList()).thenReturn(list);
        assertEquals(ResponseEntity.ok().body(list).getBody(),beerController.findAllBeersList().getBody());
    }

    @Test
    public void  should_return_status_ok_when_controller_returns_some_beer_list() {
        when(service.findAllBeersList()).thenReturn(list);
        assertEquals(HttpStatus.OK,beerController.findAllBeersList().getStatusCode());
    }



    @Test
    public void should_return_response_body_equal_to_controller_response_with_some_beer_list_base_on_brewery_id() throws NoContentException {
        when(service.findAllBeersByBreweryIdPage(breweryID, pageable)).thenReturn(page);
        assertEquals(ResponseEntity.ok().body(page).getBody(), beerController.findAllBeersByBreweryIdPage(breweryID, pageable).getBody());
    }

    @Test
    public void should_return_status_ok_when_controller_returns_some_beer_page_base_on_brewery_id() throws NoContentException {
        when(service.findAllBeersByBreweryIdPage(breweryID, pageable)).thenReturn(page);
        assertEquals(HttpStatus.OK, beerController.findAllBeersByBreweryIdPage(breweryID, pageable).getStatusCode());
    }

    @Test
    public void should_return_response_basdody_equal_to_controller_response_with_some_beer_list_base_on_brewery_id() throws NoContentException {
        when(service.findAllBeersByBreweryIdList(breweryID)).thenReturn(list);
        assertEquals(ResponseEntity.ok().body(list).getBody(), beerController.findAllBeersByBreweryIdList(breweryID).getBody());
    }

    @Test
    public void should_return_status_ok_when_controller_returns_some_beer_list_base_on_brewery_id() throws NoContentException {
        when(service.findAllBeersByBreweryIdList(breweryID)).thenReturn(list);
        assertEquals(HttpStatus.OK, beerController.findAllBeersByBreweryIdList(breweryID).getStatusCode());
    }

    //get by id

    @Test
    public void should_return_response_body_equal_to_controller_response_with_some_beer_base_on_beer_id() throws NoContentException {
        when(mapper.map(service.findBeerByBeerId(beerID), BeerDTO.class)).thenReturn(beerDTO);
        assertEquals(ResponseEntity.ok().body(beerDTO), beerController.findProperBeerByBeerId(beerID));
    }

    @Test
    public void should_return_status_ok_when_controller_returns_beer_base_on_beer_id() throws NoContentException {
        when(mapper.map(service.findBeerByBeerId(beerID), BeerDTO.class)).thenReturn(beerDTO);
        assertEquals(HttpStatus.OK, beerController.findProperBeerByBeerId(beerID).getStatusCode());
    }

    @Test(expected = NoContentException.class)
    public void should_throw_exception_when_controller_did_not_found_beer_base_on_id() throws NoContentException {
        when(service.findBeerByBeerId(beerID)).thenThrow(NoContentException.class);
        beerController.findProperBeerByBeerId(beerID);
    }

    @Test
    public void should_return_response_body_equal_to_controller_response_with_some_beer_base_on_brewery_id_and_beer_id() throws NoContentException {
        when(mapper.map(service.findProperBeerByBreweryIdAndBeerId(breweryID, beerID), BeerDTO.class)).thenReturn(beerDTO);
        assertEquals(ResponseEntity.ok().body(beerDTO).getBody(), beerController.findProperBeerBaseOnBreweryIdAndBeerId(breweryID, beerID).getBody());
    }

    @Test
    public void should_return_status_ok_when_controller_returns_some_beer_base_on_brewery_id_and_beer_id() throws NoContentException {
        when(mapper.map(service.findProperBeerByBreweryIdAndBeerId(breweryID, beerID), BeerDTO.class)).thenReturn(beerDTO);
        assertEquals(HttpStatus.OK, beerController.findProperBeerBaseOnBreweryIdAndBeerId(breweryID, beerID).getStatusCode());
    }

    @Test(expected = NoContentException.class)
    public void should_throw_exception_when_controller_did_not_found_beer_base_on_brewery_id_or_beer_id() throws NoContentException {
        when(service.findProperBeerByBreweryIdAndBeerId(breweryID, beerID)).thenThrow(NoContentException.class);
        beerController.findProperBeerBaseOnBreweryIdAndBeerId(breweryID, beerID);
    }

    //post

    @Test
    public void should_return_response_body_equal_to_controller_response_with_just_created_beer_base_on_request_body_beer() throws URISyntaxException {
        when(service.addNewBeerToRepository(mapper.map(beerDTO, Beer.class))).thenReturn(beer);
        when(mapper.map(beer, BeerDTO.class)).thenReturn(beerDTO);
        assertEquals(beerDTO, beerController.addNewBeer(beerDTO).getBody());
    }

    @Test
    public void should_return_status_created_when_controller_successfully_add_beer_base_on_request_body_beer() throws URISyntaxException {
        when(service.addNewBeerToRepository(mapper.map(beerDTO, Beer.class))).thenReturn(beer);
        when(mapper.map(beer, BeerDTO.class)).thenReturn(beerDTO);
        assertEquals(HttpStatus.CREATED, beerController.addNewBeer(beerDTO).getStatusCode());
    }

    @Test
    public void should_return_response_body_equal_to_controller_response_with_just_created_beer_base_on_request_body_beer_and_brewery_id() throws NoContentException, URISyntaxException {
        when(mapper.map(beerDTO, Beer.class)).thenReturn(beer);
        when(service.addNewBeerAssignedToBreweryByBreweryId(breweryID, mapper.map(beerDTO, Beer.class))).thenReturn(beer);
        when(mapper.map(beer, BeerDTO.class)).thenReturn(beerDTO);
        assertEquals(ResponseEntity.ok().body(beerDTO).getBody(), beerController.addNewBeerAssignedToBreweryByBreweryId(breweryID, beerDTO).getBody());
    }

    @Test
    public void should_return_status_created_when_controller_successfully_add_beer_base_on_request_body_beer_and_brewery_id() throws NoContentException, URISyntaxException {
        when(mapper.map(beerDTO, Beer.class)).thenReturn(beer);
        when(service.addNewBeerAssignedToBreweryByBreweryId(breweryID, mapper.map(beerDTO, Beer.class))).thenReturn(beer);
        when(mapper.map(beer, BeerDTO.class)).thenReturn(beerDTO);
        assertEquals(HttpStatus.CREATED, beerController.addNewBeerAssignedToBreweryByBreweryId(breweryID, beerDTO).getStatusCode());
    }

    @Test(expected = NoContentException.class)
    public void should_throw_exception_when_controller_did_not_found_brewery_base_on_brewery_id_during_add_new_beer() throws NoContentException, URISyntaxException {
        when(mapper.map(beerDTO, Beer.class)).thenReturn(beer);
        when(service.addNewBeerAssignedToBreweryByBreweryId(breweryID, mapper.map(beerDTO, Beer.class))).thenThrow(NoContentException.class);
        beerController.addNewBeerAssignedToBreweryByBreweryId(breweryID, beerDTO);
    }

    //put

    @Test
    public void should_return_response_body_equal_to_controller_response_with_just_updated_beer_base_on_request_body_beer_and_beer_id() throws NoContentException {
        when(mapper.map(beerDTO, Beer.class)).thenReturn(beer);
        when(service.updateBeerByBeerId(beerID, mapper.map(beerDTO, Beer.class))).thenReturn(beer);
        when(mapper.map(beer, BeerDTO.class)).thenReturn(beerDTO);
        assertEquals(ResponseEntity.ok().body(beerDTO).getBody(), beerController.updateBeer(beerID, beerDTO).getBody());
    }

    @Test
    public void should_return_status_ok_when_controller_successfully_updated_beer_base_on_request_body_beer_and_beer_id() throws NoContentException {
        when(mapper.map(beerDTO, Beer.class)).thenReturn(beer);
        when(service.updateBeerByBeerId(beerID, mapper.map(beerDTO, Beer.class))).thenReturn(beer);
        when(mapper.map(beer, BeerDTO.class)).thenReturn(beerDTO);
        assertEquals(HttpStatus.OK, beerController.updateBeer(beerID, beerDTO).getStatusCode());
    }

    @Test(expected = NoContentException.class)
    public void should_throw_exception_when_there_is_no_beer_base_on_id_during_updating_beer() throws NoContentException {
        when(mapper.map(beerDTO, Beer.class)).thenReturn(beer);
        when(service.updateBeerByBeerId(beerID, mapper.map(beerDTO, Beer.class))).thenThrow(NoContentException.class);
        beerController.updateBeer(beerID, beerDTO);
    }

    @Test
    public void should_return_response_body_equal_to_controller_response_with_just_updated_beer_base_on_request_body_beer_and_beer_id_and_brewery_id() throws NoContentException {
        when(mapper.map(beerDTO, Beer.class)).thenReturn(beer);
        when(service.updateBeerByBreweryIdAndBeerId(breweryID, beerID, mapper.map(beerDTO, Beer.class))).thenReturn(beer);
        when(mapper.map(beer, BeerDTO.class)).thenReturn(beerDTO);
        assertEquals(ResponseEntity.ok().body(beerDTO).getBody(), beerController.updateBeerBaseOnBreweryIdAndBeerId(breweryID, beerID, beerDTO).getBody());
    }

    @Test
    public void should_return_return_status_ok_when_controller_successfully_updated_beer_base_on_request_body_beer_and_beer_id_and_brewery_id() throws NoContentException {
        when(mapper.map(beerDTO, Beer.class)).thenReturn(beer);
        when(service.updateBeerByBreweryIdAndBeerId(breweryID, beerID, mapper.map(beerDTO, Beer.class))).thenReturn(beer);
        when(mapper.map(beer, BeerDTO.class)).thenReturn(beerDTO);
        assertEquals(HttpStatus.OK, beerController.updateBeerBaseOnBreweryIdAndBeerId(breweryID, beerID, beerDTO).getStatusCode());
    }

    @Test(expected = NoContentException.class)
    public void should_throw_exception_when_there_is_no_beer_base_on_beer_id_or_brewery_id_during_updating_beer() throws NoContentException {
        when(mapper.map(beerDTO, Beer.class)).thenReturn(beer);
        when(service.updateBeerByBreweryIdAndBeerId(breweryID, beerID, mapper.map(beerDTO, Beer.class))).thenThrow(NoContentException.class);
        beerController.updateBeerBaseOnBreweryIdAndBeerId(breweryID, beerID, beerDTO);
    }

    //delete

    @Test
    public void should_return_status_no_content_when_controller_successfully_delete_beer_base_on_beer_id() throws NoContentException {
        assertEquals(ResponseEntity.noContent().build(), beerController.deleteBeerByBeerId(beerID));
    }

    @Test
    public void should_return_status_no_content_when_controller_successfully_delete_beer_base_on_beer_id_and_brewery_id() throws NoContentException {
        assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).build(), beerController.deleteBeerByBreweryIdAndBeerId(breweryID, beerID));
    }

    @Test
    public void should_return_status_ok_when_controller_successfully_add_image_for_brewery() throws IOException, NoContentException {
        assertEquals(ResponseEntity.status(HttpStatus.OK).body("File is uploaded successfully"),beerController.uploadImage(breweryID,beerID,file));
    }

    @Test
    public void should_return_status_ok_when_controller_successfully_download_image_for_brewery() throws NoContentException {
        when(service.getBeerImageFromDbBaseOnBreweryIdAndBeerId(breweryID, beerID)).thenReturn(newArray());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        assertEquals(ResponseEntity.ok().headers(headers).body(newArray()), beerController.downloadImage(breweryID,beerID));
    }

    private byte [] newArray(){
        byte [] ds = new byte [10];
        return ds;
    }
}
