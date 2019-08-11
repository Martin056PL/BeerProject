package wawer.kamil.beerproject.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import wawer.kamil.beerproject.domain.Beer;
import wawer.kamil.beerproject.domain.Brewery;
import wawer.kamil.beerproject.domain.StyleBeer;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.repositories.BeerRepository;
import wawer.kamil.beerproject.repositories.BreweryRepository;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BeerServiceImplTest {

    @Mock
    Pageable pageable;

    @Mock
    Beer beer;

    @Mock
    Brewery brewery;



    @Mock
    BeerRepository beerRepository;

    @Mock
    BreweryRepository breweryRepository;

    @InjectMocks
    BeerServiceImpl service;

    private static final Long beerID = 1L;
    private static final Long breweryID = 1L;

    @Test
    public void asdasd() {
        service.findAllBeersPage(pageable);
        verify(beerRepository).findAll(pageable);
    }

    @Test
    public void asfe() throws NoContentException {
        when(beerRepository.existsBeerByBeerId(beerID)).thenReturn(true);
        service.findBeerByBeerId(beerID);
        verify(beerRepository).findBeerByBeerId(beerID);
    }

    @Test(expected = NoContentException.class)
    public void asfeasd() throws NoContentException {
        when(beerRepository.existsBeerByBeerId(beerID)).thenReturn(false);
        service.findBeerByBeerId(beerID);
        verify(beerRepository).findBeerByBeerId(beerID);
    }

    @Test
    public void asdw() throws NoContentException {
        when(breweryRepository.existsBreweryByBreweryId(breweryID)).thenReturn(true);
        when(breweryRepository.findByBreweryId(breweryID)).thenReturn(brewery);
        service.findAllBeersByBreweryId(breweryID, pageable);
        verify(beerRepository).findAllByBrewery(brewery, pageable);
    }

    @Test(expected = NoContentException.class)
    public void dfsdqwd() throws NoContentException {
        when(breweryRepository.existsBreweryByBreweryId(breweryID)).thenReturn(false);
        service.findAllBeersByBreweryId(breweryID, pageable);
    }

    @Test
    public void fdsd() throws NoContentException {
        when(breweryRepository.existsBreweryByBreweryId(breweryID)).thenReturn(true);
        when(beerRepository.existsBeerByBeerId(beerID)).thenReturn(true);
        when(breweryRepository.findByBreweryId(breweryID)).thenReturn(brewery);
        service.findProperBeerByBreweryIdAndBeerId(breweryID, beerID);
        verify(beerRepository).findBeerByBreweryAndBeerId(brewery, beerID);
    }

    @Test(expected = NoContentException.class)
    public void sdfwe() throws NoContentException {
        when(breweryRepository.existsBreweryByBreweryId(breweryID)).thenReturn(false);
        service.findProperBeerByBreweryIdAndBeerId(breweryID, beerID);
    }

    @Test(expected = NoContentException.class)
    public void sdfwed() throws NoContentException {
        when(breweryRepository.existsBreweryByBreweryId(breweryID)).thenReturn(true);
        when(beerRepository.existsBeerByBeerId(beerID)).thenReturn(false);
        service.findProperBeerByBreweryIdAndBeerId(breweryID, beerID);
    }

    @Test
    public void dgfdq() {
        service.addNewBeerToRepository(beer);
        beerRepository.save(beer);
    }

    @Test
    public void wefdw() throws NoContentException {
        when(breweryRepository.findById(breweryID)).thenReturn(Optional.of(brewery));
        when(beerRepository.save(beer)).thenReturn(beer);
        service.addNewBeerAssignedToBreweryByBreweryId(breweryID,beer);
        verify(breweryRepository).findById(beerID);
    }

    @Test(expected = NoContentException.class)
    public void wefdwds() throws NoContentException {
        when(breweryRepository.findById(breweryID)).thenReturn(Optional.empty());
        service.addNewBeerAssignedToBreweryByBreweryId(breweryID,beer);

    }

    //update

    @Test
    public void asdf() throws NoContentException {
        when(beerRepository.existsBeerByBeerId(beerID)).thenReturn(true);
        service.updateBeerByBeerId(breweryID,beer);
        verify(beerRepository).save(beer);
    }

    @Test(expected = NoContentException.class)
    public void fwe() throws NoContentException {
        when(beerRepository.existsBeerByBeerId(beerID)).thenReturn(false);
        service.updateBeerByBeerId(breweryID,beer);
    }

    @Test
    public void asdfew() throws NoContentException {
        when(breweryRepository.existsBreweryByBreweryId(breweryID)).thenReturn(true);
        when(beerRepository.existsBeerByBeerId(beerID)).thenReturn(true);
        when(beerRepository.findBeerByBeerId(beerID)).thenReturn(beer);
        service.updateBeerByBreweryIdAndBeerId(breweryID,beerID,beer);
        verify(beerRepository).save(beer);
    }

    @Test(expected = NoContentException.class)
    public void asdfewd() throws NoContentException {
        when(breweryRepository.existsBreweryByBreweryId(breweryID)).thenReturn(false);
        service.updateBeerByBreweryIdAndBeerId(breweryID,beerID,beer);
    }

    @Test(expected = NoContentException.class)
    public void asdfewdzxc() throws NoContentException {
        when(breweryRepository.existsBreweryByBreweryId(breweryID)).thenReturn(true);
        when(beerRepository.existsBeerByBeerId(breweryID)).thenReturn(false);
        service.updateBeerByBreweryIdAndBeerId(breweryID,beerID,beer);
    }

    //delete

    @Test
    public void sdgawd() throws NoContentException {
        when(beerRepository.existsBeerByBeerId(beerID)).thenReturn(true);
        service.deleteBeerByBeerId(beerID);
        verify(beerRepository).deleteById(beerID);
    }

    @Test(expected = NoContentException.class)
    public void sdgawdd() throws NoContentException {
        when(beerRepository.existsBeerByBeerId(beerID)).thenReturn(false);
        service.deleteBeerByBeerId(beerID);
    }

    @Test
    public void sdfwedf() throws NoContentException {
        when(breweryRepository.existsBreweryByBreweryId(breweryID)).thenReturn(true);
        when(beerRepository.existsBeerByBeerId(beerID)).thenReturn(true);
        service.deleteBeerByBreweryIdAndBeerId(breweryID,beerID);
        verify(beerRepository).deleteById(beerID);
    }

    @Test(expected = NoContentException.class)
    public void sasddfwedf() throws NoContentException {
        when(breweryRepository.existsBreweryByBreweryId(breweryID)).thenReturn(false);
        service.deleteBeerByBreweryIdAndBeerId(breweryID,beerID);
    }

    @Test(expected = NoContentException.class)
    public void sasddfweddsaf() throws NoContentException {
        when(breweryRepository.existsBreweryByBreweryId(breweryID)).thenReturn(true);
        when(beerRepository.existsBeerByBeerId(beerID)).thenReturn(false);
        service.deleteBeerByBreweryIdAndBeerId(breweryID,beerID);
    }

    private Beer returnBeer(){
        Beer beer = Beer.builder()
                .alcohol(5.2)
                .extract(12)
                .name("Porter")
                .style(StyleBeer.AmericanPorter.getStyle())
                .build();
        return beer;
    }


}
