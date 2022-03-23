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
import wawer.kamil.beerproject.dto.request.BeerRequest;
import wawer.kamil.beerproject.dto.response.BeerResponse;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.InvalidImageParameters;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.model.Brewery;
import wawer.kamil.beerproject.repositories.BeerRepository;
import wawer.kamil.beerproject.repositories.BreweryRepository;
import wawer.kamil.beerproject.service.impl.BeerServiceImpl;
import wawer.kamil.beerproject.utils.mapper.BeerMapper;
import wawer.kamil.beerproject.utils.upload.ImageUpload;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wawer.kamil.beerproject.helpers.BeerTestHelper.*;
import static wawer.kamil.beerproject.helpers.BreweryTestHelper.getSingleBreweryBeforeSave;

@ExtendWith(MockitoExtension.class)
class BeerServiceImplTest {

    @Mock
    BeerRepository beerRepository;

    @Mock
    BreweryRepository breweryRepository;

    @Mock
    BeerMapper beerMapper;

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
    private Beer beerBeforeUpdate;
    private BeerResponse beerResponse;
    private BeerResponse updatedBeerResponse;
    private BeerRequest beerRequest;

    private Page<Beer> beerPage;
    private List<Beer> beerList;
    private Brewery brewery;

    private static final Long beerID = 1L;
    private static final Long breweryID = 1L;

    @BeforeEach
    void setUp() {
        this.beer = getBeer();
        this.beerResponse = getBeerResponse();
        this.beerRequest = getBeerRequest();
        this.beerBeforeUpdate = getBeerBeforeUpdate();
        this.updatedBeerResponse = getUpdatedBeerResponse();
        this.updatedBeer = getUpdatedBeer();
        this.beerPage = getBeerPage();
        this.beerList = getListOfBeers();
        this.brewery = getSingleBreweryBeforeSave();
    }

    @Test
    @DisplayName("Verify if find all with pageable method is called during beers getting")
    void verify_if_find_all_with_pageable_method_is_called_during_beers_getting() {
        // given
        when(beerRepository.findAll(pageable)).thenReturn(beerPage);
        when(beerMapper.mapBeerToBeerResponse(beer)).thenReturn(beerResponse);

        //when
        service.findAllBeersPage(pageable);

        //then
        verify(beerRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Verify if find all method is called during beers getting")
    void verify_if_find_all_method_is_called_during_beers_getting() {
        //when
        service.findAllBeersList();

        //then
        verify(beerRepository).findAll();
    }

    @Test
    @DisplayName("Verify if find beer by beer id is called when id exists")
    void verify_find_beer_by_beer_id_when_id_exists() {
        //given
        when(beerRepository.findById(beerID)).thenReturn(Optional.of(beer));
        when(beerMapper.mapBeerToBeerResponse(beer)).thenReturn(beerResponse);

        //when
        service.findBeerById(beerID);

        //then
        verify(beerRepository).findById(beerID);
    }

    @Test
    @DisplayName("Verify if ElementNotFoundException is thrown when brewery id does not exists during getting beer by beer id")
    void verify_if_ElementNotFoundException_is_thrown_when_brewery_id_does_not_exists_during_getting_beer_by_beer_id() {
        //then
        assertThrows(ElementNotFoundException.class, this::callFindByIdWhichDoesNotExist);
    }

    private void callFindByIdWhichDoesNotExist() {
        //given
        when(beerRepository.findById(beerID)).thenReturn(Optional.empty());

        //when
        service.findBeerById(beerID);
    }

    @Test
    @DisplayName("Verify if find brewery by brewery id and find all beers with PAGEABLE are called when brewery id exists")
    void verify_find_brewery_by_brewery_id_and_find_all_beers_with_pageable_when_brewery_id_exists() {
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
    @DisplayName("Verify if ElementNotFoundException is thrown when brewery id does not exists during getting beers by brewery id with PAGEABLE")
    void verify_if_ElementNotFoundException_is_thrown_when_brewery_id_does_not_exists_during_getting_beers_by_brewery_id_with_pageable() {
        //then
        assertThrows(ElementNotFoundException.class, this::callFindAllBeersByBreweryIdPageWhichDoesNotExist);
    }

    private void callFindAllBeersByBreweryIdPageWhichDoesNotExist() {
        //given
        when(breweryRepository.findById(breweryID)).thenReturn(Optional.empty());

        //when
        service.findAllBeersByBreweryIdPage(breweryID, pageable);
    }

    @Test
    @DisplayName("Verify if find brewery by brewery id and find all beers with LIST are called when brewery id exists")
    void verify_if_find_brewery_by_brewery_id_and_find_all_beers_with_LIST_are_called_when_brewery_id_exists() {
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
    @DisplayName("Verify if ElementNotFoundException is thrown when brewery id does not exists during getting beers by brewery id with LIST")
    void verify_if_ElementNotFoundException_is_thrown_when_brewery_id_does_not_exists_during_getting_beers_by_brewery_id_with_LIST() {
        //then
        assertThrows(ElementNotFoundException.class, this::callFindAllBeersByBreweryIdListWhichDoesNotExist);
    }

    private void callFindAllBeersByBreweryIdListWhichDoesNotExist() {
        //given
        when(breweryRepository.findById(breweryID)).thenReturn(Optional.empty());

        //when
        service.findAllBeersByBreweryIdList(breweryID);
    }

    @Test
    @DisplayName("Verify if save beer is called when brewery id exists")
    void verify_save_beer_when_brewery_id_exists() {
        //given
        when(beerMapper.mapBeerRequestToBeerEntity(beerRequest)).thenReturn(beer);
        when(breweryRepository.findById(breweryID)).thenReturn(Optional.of(brewery));
        when(beerRepository.save(beer)).thenReturn(beer);
        when(beerMapper.mapBeerToBeerResponse(beer)).thenReturn(beerResponse);

        //when
        service.addNewBeerAssignedToBreweryByBreweryId(breweryID, beerRequest);

        //then
        verify(breweryRepository).findById(breweryID);
        verify(beerRepository).save(beer);
    }

    @Test
    @DisplayName("Verify if ElementNotFoundException is thrown when brewery id does not exists during saving beer")
    void verify_if_ElementNotFoundException_is_thrown_when_brewery_id_does_not_exists_during_saving_beer() {
        //then
        assertThrows(ElementNotFoundException.class, this::callAddNewBeerAssignedToBreweryByBreweryIdWhichDoesNotExist);
    }

    private void callAddNewBeerAssignedToBreweryByBreweryIdWhichDoesNotExist() {
        //given
        when(beerMapper.mapBeerRequestToBeerEntity(beerRequest)).thenReturn(beer);
        when(breweryRepository.findById(breweryID)).thenReturn(Optional.empty());

        //when
        service.addNewBeerAssignedToBreweryByBreweryId(breweryID, beerRequest);
    }


    @Test
    @DisplayName("Verify if updated beer has the same properties as on request during beer update with beer id")
    void verify_if_updated_beer_has_the_same_properties_as_on_request_during_beer_update_with_beer_id() {
        //given
        when(beerMapper.mapBeerRequestToBeerEntity(beerRequest)).thenReturn(beerBeforeUpdate);
        when(beerRepository.findById(beerID)).thenReturn(Optional.of(beer));
        when(beerMapper.mapBeerToBeerResponse(beer)).thenReturn(updatedBeerResponse);

        //when
        BeerResponse updatedBeerByService = service.updateBeerByBeerId(beerID, beerRequest);

        //then
        assertEquals(updatedBeer.getBeerId(), updatedBeerByService.getId());
        assertEquals(updatedBeer.getName(), updatedBeerByService.getName());
        assertEquals(updatedBeer.getStyle(), updatedBeerByService.getStyle());
        assertEquals(updatedBeer.getExtract(), updatedBeerByService.getExtract());
        assertEquals(updatedBeer.getAlcohol(), updatedBeerByService.getAlcohol());
    }

    @Test
    @DisplayName("Verify if ElementNotFoundException is thrown when brewery id does not exists during update beer with beer id")
    void verify_if_ElementNotFoundException_is_thrown_when_brewery_id_does_not_exists_during_update_beer_with_beer_id() {
        //then
        assertThrows(ElementNotFoundException.class, this::callUpdateBeerByBeerIdWhichDoesNotExist);
    }

    private void callUpdateBeerByBeerIdWhichDoesNotExist() {
        //given
        when(beerRepository.findById(beerID)).thenReturn(Optional.empty());

        //when
        service.updateBeerByBeerId(beerID, beerRequest);
    }

    @Test
    @DisplayName("Verify if updated beer has the same properties as on request beer during beer update with brewery and beer id")
    void verify_if_updated_beer_has_the_same_properties_as_on_request_beer_during_beer_update_with_brewery_and_beer_id() {
        //given
        when(beerMapper.mapBeerRequestToBeerEntity(beerRequest)).thenReturn(beerBeforeUpdate);
        when(beerRepository.findBeerByBreweryAndBeerId(breweryID, beerID)).thenReturn(Optional.of(beer));
        when(beerMapper.mapBeerToBeerResponse(beer)).thenReturn(updatedBeerResponse);

        //when
        BeerResponse updatedBeerByService = service.updateBeerByBreweryIdAndBeerId(breweryID, beerID, beerRequest);

        //then
        assertEquals(updatedBeer.getBeerId(), updatedBeerByService.getId());
        assertEquals(updatedBeer.getName(), updatedBeerByService.getName());
        assertEquals(updatedBeer.getStyle(), updatedBeerByService.getStyle());
        assertEquals(updatedBeer.getExtract(), updatedBeerByService.getExtract());
        assertEquals(updatedBeer.getAlcohol(), updatedBeerByService.getAlcohol());
    }

    @Test
    @DisplayName("Verify if ElementNotFoundException is thrown when brewery id does not exists during update beer with brewery and beer id")
    void verify_if_ElementNotFoundException_is_thrown_when_brewery_id_does_not_exists_during_update_beer_with_brewery_and_beer_id() {
        //then
        assertThrows(ElementNotFoundException.class, this::callUpdateBeerByBreweryIdAndBeerIdWhichDoesNotExist);
    }

    private void callUpdateBeerByBreweryIdAndBeerIdWhichDoesNotExist() {
        //given
        when(beerRepository.findBeerByBreweryAndBeerId(breweryID, beerID)).thenReturn(Optional.empty());

        //when
        service.updateBeerByBreweryIdAndBeerId(breweryID, beerID, beerRequest);
    }

    @Test
    @DisplayName("Verify if delete beer is called when beer id exists")
    void verify_delete_beer_when_beer_id_exists() {
        //given
        when(beerRepository.findById(beerID)).thenReturn(Optional.ofNullable(beer));

        //when
        service.deleteBeerById(beerID);

        //then
        verify(beerRepository).delete(beer);
    }

    @Test
    @DisplayName("Verify if ElementNotFoundException is thrown when beer id does not exists during deleting beer")
    void verify_if_ElementNotFoundException_is_thrown_when_beer_id_does_not_exists_during_deleting_beer() {
        //then
        assertThrows(ElementNotFoundException.class, this::callDeleteBeerByIdWhichDoesNotExist);
    }

    private void callDeleteBeerByIdWhichDoesNotExist() {
        //given
        when(beerRepository.findById(beerID)).thenReturn(Optional.empty());

        //when
        service.deleteBeerById(beerID);
    }

    @Test
    @DisplayName("Verify if findBeerImageByBeerId brewery is called when image base on brewery id")
    void verify_get_brewery_image_base_on_brewery_id() {
        //given
        byte[] byteArray = new byte[10];
        when(beerRepository.findBeerImageByBeerId(beerID)).thenReturn(byteArray);

        //when
        service.getBeerImageBaseOnBeerId(beerID);

        //then
        verify(beerRepository).findBeerImageByBeerId(beerID);
    }

    @Test
    @DisplayName("Verify if ElementNotFoundException is thrown when beer id does not exists during getting beer image")
    void verify_if_ElementNotFoundException_is_thrown_when_beer_id_does_not_exists_during_getting_beer_image() {
        //then
        assertThrows(ElementNotFoundException.class, this::callGetBeerImageBaseOnBeerIdWhichDoesNotExist);
    }

    private void callGetBeerImageBaseOnBeerIdWhichDoesNotExist() {
        //given
        when(beerRepository.findBeerImageByBeerId(beerID)).thenReturn(null);

        //when
        service.getBeerImageBaseOnBeerId(beerID);
    }

    @Test
    @DisplayName("Verify if findById is called when setting image to existing beer")
    void verify_findById_when_setting_image_to_existing_beer() throws IOException {
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
    @DisplayName("Verify if ElementNotFoundException is thrown when set image brewery by beer id does not exists")
    void verify_set_image_brewery_by_id_when_brewery_id_do_not_exists() {
        //then
        assertThrows(InvalidImageParameters.class, this::callSetBeerImageToBeerByBeerIdWhichHasInvalidFile);
    }

    private void callSetBeerImageToBeerByBeerIdWhichHasInvalidFile() throws IOException {
        //given
        when(beerRepository.findById(beerID)).thenReturn(Optional.of(beer));
        when(imageUpload.validateFile(file)).thenReturn(false);

        //when
        service.setBeerImageToBeerByBeerId(beerID, file);
    }
}
