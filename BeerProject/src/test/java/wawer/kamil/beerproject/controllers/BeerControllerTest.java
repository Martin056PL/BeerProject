package wawer.kamil.beerproject.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import wawer.kamil.beerproject.domain.Beer;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.service.BeerServiceImpl;

import java.net.URISyntaxException;

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
    Beer beer;

    @InjectMocks
    BeerController beerController;

    private static final Long beerID = 1L;
    private static final Long breweryID = 1L;

    //get all

    @Test
    public void should_body_response_be_equal_to_response_given_by_controller() {
        when(service.findAllBeersPage(pageable)).thenReturn(page);
        assertEquals(ResponseEntity.ok().body(page), beerController.findAllBeers(pageable));
    }

    @Test
    public void should_return_status_ok_when_controller_returns_some_beer_list() {
        assertEquals(HttpStatus.OK, beerController.findAllBeers(pageable).getStatusCode());
    }

    @Test
    public void should_return_response_body_equal_to_controller_response_with_some_beer_list_base_on_brewery_id() throws NoContentException {
        when(service.findAllBeersByBreweryId(breweryID, pageable)).thenReturn(page);
        assertEquals(ResponseEntity.ok().body(page).getBody(), beerController.findAllBeersByBreweryId(breweryID, pageable).getBody());
    }

    @Test
    public void should_return_status_ok_when_controller_returns_some_beer_list_base_on_brewery_id() throws NoContentException {
        when(service.findAllBeersByBreweryId(breweryID, pageable)).thenReturn(page);
        assertEquals(HttpStatus.OK, beerController.findAllBeersByBreweryId(breweryID, pageable).getStatusCode());
    }

    //get by id

    @Test
    public void should_return_response_body_equal_to_controller_response_with_some_beer_base_on_beer_id() throws NoContentException {
        when(service.findBeerByBeerId(beerID)).thenReturn(beer);
        assertEquals(ResponseEntity.ok().body(beer), beerController.findProperBeerByBeerId(beerID));
    }

    @Test
    public void should_return_status_ok_when_controller_returns_beer_base_on_beer_id() throws NoContentException {
        when(service.findBeerByBeerId(beerID)).thenReturn(beer);
        assertEquals(HttpStatus.OK, beerController.findProperBeerByBeerId(beerID).getStatusCode());
    }

    @Test(expected = NoContentException.class)
    public void should_throw_exception_when_controller_did_not_found_beer_base_on_id() throws NoContentException {
        when(service.findBeerByBeerId(beerID)).thenThrow(NoContentException.class);
        beerController.findProperBeerByBeerId(beerID);
    }

    @Test
    public void should_return_response_body_equal_to_controller_response_with_some_beer_base_on_brewery_id_and_beer_id() throws NoContentException {
        when(service.findProperBeerByBreweryIdAndBeerId(breweryID,beerID)).thenReturn(beer);
        assertEquals(ResponseEntity.ok().body(beer).getBody(), beerController.findProperBeerBaseOnBreweryIdAndBeerId(breweryID,beerID).getBody());
    }

    @Test
    public void should_return_status_ok_when_controller_returns_some_beer_base_on_brewery_id_and_beer_id() throws NoContentException {
        when(service.findProperBeerByBreweryIdAndBeerId(breweryID,beerID)).thenReturn(beer);
        assertEquals(HttpStatus.OK, beerController.findProperBeerBaseOnBreweryIdAndBeerId(breweryID,beerID).getStatusCode());
    }

    @Test(expected = NoContentException.class)
    public void should_throw_exception_when_controller_did_not_found_beer_base_on_brewery_id_or_beer_id() throws NoContentException {
        when(service.findProperBeerByBreweryIdAndBeerId(breweryID,beerID)).thenThrow(NoContentException.class);
        beerController.findProperBeerBaseOnBreweryIdAndBeerId(breweryID,beerID);
    }

    //post

    @Test
    public void should_return_response_body_equal_to_controller_response_with_just_created_beer_base_on_request_body_beer() throws URISyntaxException {
        when(service.addNewBeerToRepository(beer)).thenReturn(beer);
        assertEquals(beer, beerController.addNewBeer(beer).getBody());
    }

    @Test
    public void should_return_status_created_when_controller_successfully_add_beer_base_on_request_body_beer() throws URISyntaxException {
        when(service.addNewBeerToRepository(beer)).thenReturn(beer);
        assertEquals(HttpStatus.CREATED, beerController.addNewBeer(beer).getStatusCode());
    }

    @Test
    public void should_return_response_body_equal_to_controller_response_with_just_created_beer_base_on_request_body_beer_and_brewery_id() throws NoContentException, URISyntaxException {
        when(service.addNewBeerAssignedToBreweryByBreweryId(breweryID,beer)).thenReturn(beer);
        assertEquals(ResponseEntity.ok().body(beer).getBody(),beerController.AddNewBeerAssignedToBreweryByBreweryId(breweryID,beer).getBody());
    }

    @Test
    public void should_return_status_created_when_controller_successfully_add_beer_base_on_request_body_beer_and_brewery_id() throws NoContentException, URISyntaxException {
        when(service.addNewBeerAssignedToBreweryByBreweryId(breweryID,beer)).thenReturn(beer);
        assertEquals(HttpStatus.CREATED,beerController.AddNewBeerAssignedToBreweryByBreweryId(breweryID,beer).getStatusCode());
    }

    @Test(expected = NoContentException.class)
    public void should_throw_exception_when_controller_did_not_found_brewery_base_on_brewery_id_during_add_new_beer() throws NoContentException, URISyntaxException {
        when(service.addNewBeerAssignedToBreweryByBreweryId(breweryID,beer)).thenThrow(NoContentException.class);
        beerController.AddNewBeerAssignedToBreweryByBreweryId(breweryID,beer);
    }

    //put

    @Test
    public void should_return_response_body_equal_to_controller_response_with_just_updated_beer_base_on_request_body_beer_and_beer_id() throws NoContentException {
        when(service.updateBeerByBeerId(beerID, beer)).thenReturn(beer);
        assertEquals(ResponseEntity.ok().body(beer).getBody(), beerController.updateBeer(beerID, beer).getBody());
    }

    @Test
    public void should_return_status_ok_when_controller_successfully_updated_beer_base_on_request_body_beer_and_beer_id() throws NoContentException {
        when(service.updateBeerByBeerId(beerID, beer)).thenReturn(beer);
        assertEquals(HttpStatus.OK, beerController.updateBeer(beerID, beer).getStatusCode());
    }

    @Test(expected = NoContentException.class)
    public void should_throw_exception_when_there_is_no_beer_base_on_id_during_updating_beer() throws NoContentException {
        when(service.updateBeerByBeerId(beerID, beer)).thenThrow(NoContentException.class);
        beerController.updateBeer(beerID, beer);
    }

    @Test
    public void should_return_response_body_equal_to_controller_response_with_just_updated_beer_base_on_request_body_beer_and_beer_id_and_brewery_id() throws NoContentException {
        when(service.updateBeerByBreweryIdAndBeerId(breweryID,beerID,beer)).thenReturn(beer);
        assertEquals(ResponseEntity.ok().body(beer).getBody(),beerController.updateBeerBaseOnBreweryIdAndBeerId(breweryID,beerID,beer).getBody());
    }

    @Test
    public void should_return_return_status_ok_when_controller_successfully_updated_beer_base_on_request_body_beer_and_beer_id_and_brewery_id() throws NoContentException {
        when(service.updateBeerByBreweryIdAndBeerId(breweryID,beerID,beer)).thenReturn(beer);
        assertEquals(HttpStatus.OK,beerController.updateBeerBaseOnBreweryIdAndBeerId(breweryID,beerID,beer).getStatusCode());
    }

    @Test(expected = NoContentException.class)
    public void should_throw_exception_when_there_is_no_beer_base_on_beer_id_orbrewery_id_during_updating_beer() throws NoContentException {
        when(service.updateBeerByBreweryIdAndBeerId(breweryID,beerID,beer)).thenThrow(NoContentException.class);
        beerController.updateBeerBaseOnBreweryIdAndBeerId(breweryID,beerID,beer);
    }

    //delete

    @Test
    public void should_return_status_no_content_when_controller_successfully_delete_beer_base_on_beer_id() throws NoContentException {
        assertEquals(ResponseEntity.noContent().build(), beerController.deleteBeerByBeerId(beerID));
    }

    @Test
    public void should_return_status_no_content_when_controller_successfully_delete_beer_base_on_beer_id_and_brewery_id() throws NoContentException {
        assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).build(),beerController.deleteBeerByBreweryIdAndBeerId(breweryID,beerID));
    }
}
