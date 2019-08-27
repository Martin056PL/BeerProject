package wawer.kamil.beerproject.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import wawer.kamil.beerproject.domain.Brewery;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.repositories.BreweryRepository;

import org.springframework.data.domain.Pageable;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BreweryServiceImplTest {

    @Mock
    BreweryRepository repository;

    @Mock
    Pageable pageable;

    @Mock
    Brewery brewery;

    @InjectMocks
    BreweryServiceImpl service;

    private final static Long ID = 1L;

    @Test
    public void verify_get_all_breweryPage(){
        service.getAllBreweryPage(pageable);
        verify(repository).findAll(pageable);
    }

    @Test
    public void verify_get_all_breweryList(){
        service.getAllBreweryList();
        verify(repository).findAll();
    }

    @Test
    public void verify_get_brewery_by_brewery_id_when_brewery_id_exists() throws NoContentException {
        when(repository.existsBreweryByBreweryId(ID)).thenReturn(true);
        service.getBreweryByBreweryId(ID);
        verify(repository).findByBreweryId(ID);
    }

    @Test(expected = NoContentException.class)
    public void verify_get_brewery_by_brewery_id_when_brewery_id_do_not_exists() throws NoContentException {
        when(repository.existsBreweryByBreweryId(ID)).thenReturn(false);
        service.getBreweryByBreweryId(ID);
        verify(repository).findByBreweryId(ID);
    }

    @Test
    public void verify_create_new_brewery(){
        service.createNewBrewery(brewery);
        verify(repository).save(brewery);
    }

    @Test
    public void verify_update_brewery_by_brewery_id_when_brewery_id_exists() throws NoContentException {
        when(repository.existsBreweryByBreweryId(ID)).thenReturn(true);
        service.updateBreweryById(ID,brewery);
        verify(repository).save(brewery);
    }

    @Test(expected = NoContentException.class)
    public void verify_update_brewery_by_brewery_id_when_brewery_id_do_not_exists() throws NoContentException {
        when(repository.existsBreweryByBreweryId(ID)).thenReturn(false);
        service.updateBreweryById(ID,brewery);
        verify(repository).save(brewery);
    }

    @Test
    public void verify_delete_brewery_by_brewery_id_when_brewery_id_exists() throws NoContentException {
        when(repository.existsBreweryByBreweryId(ID)).thenReturn(true);
        service.deleteBreweryByBreweryId(ID);
        verify(repository).deleteById(ID);
    }

    @Test(expected = NoContentException.class)
    public void verify_delete_brewery_by_brewery_id_when_brewery_id_do_not_exists() throws NoContentException {
        when(repository.existsBreweryByBreweryId(ID)).thenReturn(false);
        service.deleteBreweryByBreweryId(ID);
        verify(repository).deleteById(ID);
    }

    @Test
    public void verify_get_brewery_image_from_db_base_on_brewery_id() throws NoContentException {
        when(repository.existsBreweryByBreweryId(ID)).thenReturn(true);
        when(service.getBreweryByBreweryId(ID)).thenReturn(brewery);
        assertEquals(brewery.getBreweryImage(), service.getBreweryImageFromDbBaseOnBreweryId(ID));
    }
}
