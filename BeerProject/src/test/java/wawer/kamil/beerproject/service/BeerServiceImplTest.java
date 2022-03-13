package wawer.kamil.beerproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.model.Brewery;
import wawer.kamil.beerproject.repositories.BeerRepository;
import wawer.kamil.beerproject.repositories.BreweryRepository;
import wawer.kamil.beerproject.service.impl.BeerServiceImpl;
import wawer.kamil.beerproject.utils.upload.ImageUpload;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static wawer.kamil.beerproject.controllers.BeerTestHelper.*;
import static wawer.kamil.beerproject.controllers.BreweryTestHelper.getSingleBreweryBeforeSave;

@ExtendWith(MockitoExtension.class)
class BeerServiceImplTest {

    @Mock
    BeerRepository beerRepository;

    @Mock
    BreweryRepository breweryRepository;

    @Mock
    Pageable pageable;

    @Mock
    MultipartFile file;

    @Mock
    ImageUpload imageUpload;

    @InjectMocks
    BeerServiceImpl service;

    private Beer beer;
    private Beer updatedBeer;
    private Page<Beer> beerPage;
    private List<Beer> beerList;
    private Brewery brewery;

    private static final Long beerID = 1L;
    private static final Long breweryID = 1L;

    @BeforeEach
    void setUp() {
        this.beer = getBeer();
        this.updatedBeer = getUpdatedBeer();
        this.beerPage = getBeerPage();
        this.beerList = getListOfBeers();
        this.brewery = getSingleBreweryBeforeSave();
    }

    //
    // TODO It is needed to rename all tests
    //

    @Test
    void verify_find_all_beers_page() {
        //when
        service.findAllBeersPage(pageable);

        //then
        verify(beerRepository).findAll(pageable);
    }

    @Test
    void verify_find_all_beers_list() {
        //when
        service.findAllBeersList();

        //then
        verify(beerRepository).findAll();
    }

    @Test
    void verify_find_beer_by_beer_id_when_id_exists() throws ElementNotFoundException {
        //given
        when(beerRepository.findById(beerID)).thenReturn(Optional.of(beer));

        //when
        service.findBeerById(beerID);

        //then
        verify(beerRepository).findById(beerID);
    }

    @Test
    @DisplayName("Verify if ElementNotFoundException is thrown when brewery id does not exists during update")
    void verify_update_brewery_by_brewery_id_when_brewery_id_do_not_exists_during_update() {
        //then
        assertThrows(ElementNotFoundException.class, this::callFindByIdWhichDoesNotExist);
    }

    private void callFindByIdWhichDoesNotExist() throws ElementNotFoundException {
        //given
        when(beerRepository.findById(beerID)).thenReturn(Optional.empty());

        //when
        service.findBeerById(beerID);
    }

    @Test
    void verify_find_all_beers_by_brewery_id_when_id_exists() throws ElementNotFoundException {
        //given
        when(breweryRepository.findById(breweryID)).thenReturn(Optional.of(brewery));
        when(beerRepository.findAllByBreweryId(brewery.getBreweryId(), pageable)).thenReturn(beerPage);

        //when
        service.findAllBeersByBreweryIdPage(breweryID, pageable);

        //then
        verify(breweryRepository).findById(breweryID);
        verify(beerRepository).findAllByBreweryId(brewery.getBreweryId(), pageable);
    }

    @Test
    @DisplayName("d123")
    void d123() {
        //then
        assertThrows(ElementNotFoundException.class, this::asd);
    }

    private void asd() throws ElementNotFoundException {
        //given
        when(breweryRepository.findById(breweryID)).thenReturn(Optional.empty());

        //when
        service.findAllBeersByBreweryIdPage(breweryID, pageable);
    }

    @Test
    void zxc() throws ElementNotFoundException {
        //given
        when(breweryRepository.findById(breweryID)).thenReturn(Optional.of(brewery));
        when(beerRepository.findAllByBreweryId(brewery.getBreweryId())).thenReturn(beerList);

        //when
        service.findAllBeersByBreweryIdList(breweryID);

        //then
        verify(breweryRepository).findById(breweryID);
        verify(beerRepository).findAllByBreweryId(brewery.getBreweryId());
    }

    @Test
    @DisplayName("zxc1")
    void zxc1() {
        //then
        assertThrows(ElementNotFoundException.class, this::xcv);
    }

    private void xcv() throws ElementNotFoundException {
        //given
        when(breweryRepository.findById(breweryID)).thenReturn(Optional.empty());

        //when
        service.findAllBeersByBreweryIdList(breweryID);
    }

    @Test
    void verify_find_proper_beer_by_brewery_id_and_beerId_when_both_ids_exist() throws ElementNotFoundException {
        //given
        when(breweryRepository.findById(breweryID)).thenReturn(Optional.of(brewery));

        //when
        service.addNewBeerAssignedToBreweryByBreweryId(breweryID, beer);

        //then
        verify(beerRepository).save(beer);
    }

    @Test
    @DisplayName("xcv1")
    void xcv1() {
        //then
        assertThrows(ElementNotFoundException.class, this::cvb);
    }

    private void cvb() throws ElementNotFoundException {
        //given
        when(breweryRepository.findById(breweryID)).thenReturn(Optional.empty());

        //when
        service.findAllBeersByBreweryIdList(breweryID);
    }


    @Test
    void verify_update_beer_by_beer_id_when_id_exists() throws ElementNotFoundException {
        //given
        when(beerRepository.findById(beerID)).thenReturn(Optional.of(beer));

        //when
        Beer updatedBeerByService = service.updateBeerByBeerId(beerID, updatedBeer);

        //then
        assertEquals(updatedBeer.getBeerId(), updatedBeerByService.getBeerId());
        assertEquals(updatedBeer.getName(), updatedBeerByService.getName());
        assertEquals(updatedBeer.getStyle(), updatedBeerByService.getStyle());
        assertEquals(updatedBeer.getBrewery(), updatedBeerByService.getBrewery());
        assertEquals(updatedBeer.getExtract(), updatedBeerByService.getExtract());
        assertEquals(updatedBeer.getAlcohol(), updatedBeerByService.getAlcohol());
    }

    @Test
    @DisplayName("abcd1234")
    void abcd1234() {
        //then
        assertThrows(ElementNotFoundException.class, this::zxccvb);
    }

    private void zxccvb() throws ElementNotFoundException {
        //given
        when(beerRepository.findById(beerID)).thenReturn(Optional.empty());

        //when
        service.updateBeerByBeerId(beerID, updatedBeer);
    }

    @Test
    void vzxczxc() throws ElementNotFoundException {
        //given
        when(beerRepository.findBeerByBreweryAndBeerId(breweryID, beerID)).thenReturn(Optional.of(beer));

        //when
        Beer updatedBeerByService = service.updateBeerByBreweryIdAndBeerId(breweryID, beerID, updatedBeer);

        //then
        assertEquals(updatedBeer.getBeerId(), updatedBeerByService.getBeerId());
        assertEquals(updatedBeer.getName(), updatedBeerByService.getName());
        assertEquals(updatedBeer.getStyle(), updatedBeerByService.getStyle());
        assertEquals(updatedBeer.getBrewery(), updatedBeerByService.getBrewery());
        assertEquals(updatedBeer.getExtract(), updatedBeerByService.getExtract());
        assertEquals(updatedBeer.getAlcohol(), updatedBeerByService.getAlcohol());
    }

    @Test
    @DisplayName("abcdxcvxcvxvcx1234")
    void xcvxcvx() {
        //then
        assertThrows(ElementNotFoundException.class, this::cvbcvb);
    }

    private void cvbcvb() throws ElementNotFoundException {
        //given
        when(beerRepository.findBeerByBreweryAndBeerId(breweryID, beerID)).thenReturn(Optional.empty());

        //when
        service.updateBeerByBreweryIdAndBeerId(breweryID, beerID, updatedBeer);
    }

    @Test
    void xcvxcvxcvxcvcx() throws ElementNotFoundException {
        //given
        when(beerRepository.findById(beerID)).thenReturn(Optional.ofNullable(beer));

        //when
        service.deleteBeerById(beerID);

        //then
        verify(beerRepository).delete(beer);
    }

    @Test
    @DisplayName("asdasdasdads")
    void asdasdasdasd() {
        //then
        assertThrows(ElementNotFoundException.class, this::asdzxc);
    }

    private void asdzxc() throws ElementNotFoundException {
        //given
        when(beerRepository.findById(beerID)).thenReturn(Optional.empty());

        //when
        service.deleteBeerById(beerID);
    }

    @Test
    @DisplayName("Verify get brewery image from db base on brewery id")
    void verify_get_brewery_image_from_db_base_on_brewery_id() throws ElementNotFoundException {
        //given
        byte[] byteArray = new byte[10];
        when(beerRepository.findBeerImageByBeerId(beerID)).thenReturn(byteArray);

        //when
        service.getBeerImageBaseOnBeerId(beerID);

        //then
        verify(beerRepository).findBeerImageByBeerId(beerID);
    }

    @Test
    @DisplayName("sdfsdf")
    void sdfsdf() {
        //then
        assertThrows(ElementNotFoundException.class, this::dfgdfdf);
    }

    private void dfgdfdf() throws ElementNotFoundException {
        //given
        when(beerRepository.findBeerImageByBeerId(beerID)).thenReturn(null);

        //when
        service.getBeerImageBaseOnBeerId(beerID);
    }

    @Test
    void cvcv() throws ElementNotFoundException, IOException, InvalidImageParameters {
        //given
        byte[] byteArray = new byte[10];
        when(beerRepository.findById(beerID)).thenReturn(Optional.of(beer));
        when(imageUpload.validateFile(file)).thenReturn(true);
        when(imageUpload.convertImageToByteArray(file)).thenReturn(byteArray);

        //when
        service.setBeerImageToBeerByBeerId(beerID, file);

        //then
        verify(beerRepository).findById(beerID);
    }

    @Test
    @DisplayName("Verify set image brewery by id when brewery id do not exists")
    void asdasdasd() {
        //then
        assertThrows(InvalidImageParameters.class, this::asdasd);
    }

    private void asdasd() throws ElementNotFoundException, InvalidImageParameters, IOException {
        //given
        when(beerRepository.findById(beerID)).thenReturn(Optional.of(beer));
        when(imageUpload.validateFile(file)).thenReturn(false);

        //when
        service.setBeerImageToBeerByBeerId(beerID, file);
    }
}
