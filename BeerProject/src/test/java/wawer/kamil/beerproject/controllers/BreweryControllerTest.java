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
import wawer.kamil.beerproject.domain.Brewery;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.service.BreweryServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BreweryControllerTest {

    @Mock
    BreweryServiceImpl service;

    @Mock
    Page<Brewery> page;

    @Mock
    Pageable pageable;

    @Mock
    Brewery brewery;

    @InjectMocks
    BreweryController controller;

    private final static Long ID = 1L;

    @Test
    public void should_return_response_entity_which_equals_to_controller_response_entity() throws NoContentException {
        when(service.getAllBrewery(pageable)).thenReturn(page);
        assertEquals(ResponseEntity.ok().body(page), controller.getAllBrewery(pageable));
    }

    @Test
    public void should_return_status_code_which_equals_to_controller_status_code() throws NoContentException {
        when(service.getAllBrewery(pageable)).thenReturn(page);
        assertEquals(HttpStatus.OK, controller.getAllBrewery(pageable).getStatusCode());
    }

    @Test
    public void should_return_body_response_which_equals_to_controller_body_response() throws NoContentException {
        when(service.getAllBrewery(pageable)).thenReturn(page);
        assertEquals(ResponseEntity.ok().body(page).getBody(), controller.getAllBrewery(pageable).getBody());
    }

    @Test
    public void should_return_response_entity_which_equals_to_controller_response_entity_base_on_brewery_id() throws NoContentException {
        when(service.getBreweryByBreweryId(ID)).thenReturn(brewery);
        assertEquals(ResponseEntity.ok().body(brewery), controller.getBreweryByBreweryId(ID));
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

    /*@Test
    public void should_return_status_created_when_controller_add_new_brewery() throws URISyntaxException {
        when(service.createNewBrewery(brewery)).thenReturn(brewery);
        assertEquals(HttpStatus.CREATED, controller.addNewBrewery(brewery).getStatusCode());
    }*/

    /*@Test
    public void should_return_saved_brewery_which_equals_to_brewery_saved_by_controller() throws URISyntaxException {
        URI uri = new URI("add-beer" + brewery.getBreweryId());
        when(service.createNewBrewery(brewery)).thenReturn(brewery);
        assertEquals(ResponseEntity.created(uri).body(brewery).getBody(), controller.addNewBrewery(brewery).getBody());
    }*/

    /*@Test
    public void should_body_response_be_equal_to_response_from_controller() throws NoContentException {
        when(service.updateBreweryById(ID, brewery)).thenReturn(brewery);
        assertEquals(ResponseEntity.ok().body(brewery), controller.updateBrewery(ID, brewery));
    }

    @Test
    public void should_status_be_ok_and_be_equal_to_status_retured_by_controller() throws NoContentException {
        when(service.updateBreweryById(ID, brewery)).thenReturn(brewery);
        assertEquals(HttpStatus.OK, controller.updateBrewery(ID, brewery).getStatusCode());
    }

    @Test(expected = NoContentException.class)
    public void should_throw_exception_when_there_is_no_brewery_base_on_brewery_id_during_update() throws NoContentException {
        when(service.updateBreweryById(ID, brewery)).thenThrow(NoContentException.class);
        controller.updateBrewery(ID, brewery);
    }*/

    @Test
    public void should_status_be_no_content_when_controller_deleted_brewery_by_brewery_id() throws NoContentException {
        assertEquals(HttpStatus.NO_CONTENT, controller.deleteBrewery(ID).getStatusCode());
    }
}
