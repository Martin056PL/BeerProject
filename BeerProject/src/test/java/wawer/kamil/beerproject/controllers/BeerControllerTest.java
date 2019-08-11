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
    public void should_be_equal_with_content_from_db_when_controller_returns_content() {
        when(service.findAllBeersPage(pageable)).thenReturn(page);
        assertEquals(ResponseEntity.ok().body(page), beerController.findAllBeers(pageable));
    }

    @Test
    public void should_return_status_ok_when_controller_returns_the_same_content() {
        assertEquals(HttpStatus.OK, beerController.findAllBeers(pageable).getStatusCode());
    }

    @Test
    public void should_return_status_ok_which_equals_status_code_returned_from_controller() throws NoContentException {
        when(service.findAllBeersByBreweryId(breweryID, pageable)).thenReturn(page);
        assertEquals(HttpStatus.OK, beerController.findAllBeersByBreweryId(breweryID, pageable).getStatusCode());
    }

    @Test
    public void should_return_response_body_() throws NoContentException {
        when(service.findAllBeersByBreweryId(breweryID, pageable)).thenReturn(page);
        assertEquals(ResponseEntity.ok().body(page).getBody(), beerController.findAllBeersByBreweryId(breweryID, pageable).getBody());
    }

    //get by id

    @Test
    public void should_return_proper_beer_base_on_id_from_db_when_controller_returns_beer() throws NoContentException {
        when(service.findBeerByBeerId(beerID)).thenReturn(beer);
        assertEquals(ResponseEntity.ok().body(beer), beerController.findProperBeerByBeerId(beerID));
    }

    @Test(expected = NoContentException.class)
    public void should_throw_exception_when_controller_did_not_found_beer_base_on_id() throws NoContentException {
        when(service.findBeerByBeerId(beerID)).thenThrow(NoContentException.class);
        beerController.findProperBeerByBeerId(beerID);
    }

    @Test
    public void asadfwe() throws NoContentException {
        when(service.findProperBeerByBreweryIdAndBeerId(breweryID,beerID)).thenReturn(beer);
        assertEquals(ResponseEntity.ok().body(beer).getBody(), beerController.findProperBeerBaseOnBreweryIdAndBeerId(breweryID,beerID).getBody());
    }

    @Test(expected = NoContentException.class)
    public void ergwe() throws NoContentException {
        when(service.findProperBeerByBreweryIdAndBeerId(breweryID,beerID)).thenThrow(NoContentException.class);
        beerController.findProperBeerBaseOnBreweryIdAndBeerId(breweryID,beerID);
    }

    //post

    @Test
    public void should_response_be_equal_to_response_body_from_controller() throws URISyntaxException {
        when(service.addNewBeerToRepository(beer)).thenReturn(beer);
        assertEquals(beer, beerController.addNewBeer(beer).getBody());
    }

    @Test
    public void  asdgwer() throws NoContentException {
        when(service.addNewBeerAssignedToBreweryByBreweryId(breweryID,beer)).thenReturn(beer);
        assertEquals(ResponseEntity.ok().body(beer),beerController.AddNewBeerAssignedToBreweryByBreweryId(breweryID,beer));
    }

    @Test(expected = NoContentException.class)
    public void dsfsd() throws NoContentException {
        when(service.addNewBeerAssignedToBreweryByBreweryId(breweryID,beer)).thenThrow(NoContentException.class);
        beerController.AddNewBeerAssignedToBreweryByBreweryId(breweryID,beer);
    }

    //put

    @Test
    public void should_properly_update_beer_base_on_delivered_beer_id() throws NoContentException {
        when(service.updateBeerByBeerId(beerID, beer)).thenReturn(beer);
        assertEquals(ResponseEntity.ok().body(beer), beerController.updateBeer(beerID, beer));
    }

    @Test(expected = NoContentException.class)
    public void should_throw_exception_when_there_is_no_beer_base_on_id() throws NoContentException {
        when(service.updateBeerByBeerId(beerID, beer)).thenThrow(NoContentException.class);
        beerController.updateBeer(beerID, beer);
    }

    @Test
    public void asdasdasd() throws NoContentException {
        when(service.updateBeerByBreweryIdAndBeerId(breweryID,beerID,beer)).thenReturn(beer);
        assertEquals(ResponseEntity.ok().body(beer),beerController.updateBeerBaseOnBreweryIdAndBeerId(breweryID,beerID,beer));
    }

    @Test(expected = NoContentException.class)
    public void vsdfw() throws NoContentException {
        when(service.updateBeerByBreweryIdAndBeerId(breweryID,beerID,beer)).thenThrow(NoContentException.class);
        beerController.updateBeerBaseOnBreweryIdAndBeerId(breweryID,beerID,beer);
    }

    //delete

    @Test
    public void should_return_status_no_content_when_controller_successfully_delete_beer_base_on_id() throws NoContentException {
        assertEquals(ResponseEntity.noContent().build(), beerController.deleteBeerByBeerId(beerID));
    }

    @Test
    public void fdsfqwe() throws NoContentException {
        assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).build(),beerController.deleteBeerByBreweryIdAndBeerId(breweryID,beerID));
    }
}
