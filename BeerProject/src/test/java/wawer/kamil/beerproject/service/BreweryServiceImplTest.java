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
import wawer.kamil.beerproject.service.impl.BreweryServiceImpl;
import wawer.kamil.beerproject.utils.upload.ImageUpload;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static wawer.kamil.beerproject.controllers.BeerTestHelper.getListOfBeers;
import static wawer.kamil.beerproject.controllers.BreweryTestHelper.*;

@ExtendWith(MockitoExtension.class)
class BreweryServiceImplTest {

    @Mock
    BreweryRepository breweryRepository;

    @Mock
    BeerRepository beerRepository;

    @Mock
    Pageable pageable;

    @Mock
    MultipartFile file;

    @Mock
    ImageUpload imageUpload;

    @InjectMocks
    BreweryServiceImpl service;

    private Page<Brewery> breweryPage;
    private List<Long> ids;
    private List<Beer> listOfBearsBaseOnIDs;
    private List<Brewery> breweryList;
    private Brewery singleSavedBrewery;
    private Brewery singleBreweryBeforeSave;

    private final static Long ID = 1L;

    @BeforeEach
    void setUp() {
        this.breweryPage = getBreweryPage();
        this.ids = List.of(ID);
        this.listOfBearsBaseOnIDs = getListOfBeers();
        this.breweryList = getBreweryList();
        this.singleSavedBrewery = getSingleSavedBrewery();
        this.singleBreweryBeforeSave = getSingleBreweryBeforeSave();
    }

    //
    // TODO Rename tests
    //

    @Test
    @DisplayName("Verify if find all with beers for pageable method is called during getting page of breweries")
    void verify_if_find_all_with_beers_for_pageable_method_is_called() {
        //given
        when(breweryRepository.findAll(pageable)).thenReturn(breweryPage);
        when(beerRepository.findBeersByListOfBreweriesId(ids)).thenReturn(listOfBearsBaseOnIDs);

        //when
        service.getAllBreweryPage(pageable);

        //then
        verify(breweryRepository).findAll(pageable);
        verify(beerRepository).findBeersByListOfBreweriesId(List.of(ID));
    }

    @Test
    @DisplayName("Verify if findAll with beers for list method is called during getting list of breweries")
    void verify_if_find_all_with_beers_for_list_method_is_called() {
        //given
        when(breweryRepository.findAll()).thenReturn(breweryList);
        when(beerRepository.findBeersByListOfBreweriesId(ids)).thenReturn(listOfBearsBaseOnIDs);

        //when
        service.getAllBreweryList();

        //then
        verify(breweryRepository).findAll();
    }

    @Test
    @DisplayName("Verify if findById method is called during getting single brewery by id")
    void verify_get_brewery_by_brewery_id_when_brewery_id_exists() throws ElementNotFoundException {
        //given
        when(breweryRepository.findById(ID)).thenReturn(Optional.of(singleSavedBrewery));

        //when
        service.findBreweryById(ID);

        //then
        verify(breweryRepository).findById(ID);
    }

    @Test
    @DisplayName("Verify if save method is called during brewery creation")
    void verify_create_new_brewery() {
        //given
        when(breweryRepository.save(singleBreweryBeforeSave)).thenReturn(singleSavedBrewery);

        //when
        service.createNewBrewery(singleBreweryBeforeSave);

        //then
        verify(breweryRepository).save(singleBreweryBeforeSave);
    }

    @Test
    @DisplayName("Verify update brewery by brewery id when brewery id does not exists")
    void verify_update_brewery_by_brewery_id_when_brewery_id_exists() throws ElementNotFoundException {
        //given
        when(breweryRepository.findById(ID)).thenReturn(Optional.of(singleSavedBrewery));

        //when
        service.updateBreweryById(ID, getSingleBreweryBeforeSave());

        //then
        verify(breweryRepository).findById(ID);
    }

    @Test
    @DisplayName("Verify if ElementNotFoundException is thrown when brewery id does not exists during update")
    void verify_update_brewery_by_brewery_id_when_brewery_id_do_not_exists_during_update() {
        assertThrows(ElementNotFoundException.class, this::callUpdateBreweryByIdWhichDoesNotExist);
    }

    private void callUpdateBreweryByIdWhichDoesNotExist() throws ElementNotFoundException {
        when(breweryRepository.findById(ID)).thenReturn(Optional.empty());
        service.updateBreweryById(ID, getSingleBreweryBeforeSave());
    }

    @Test
    @DisplayName("Verify if delete method is called during brewery deletion")
    void verify_delete_brewery_by_brewery_id_when_brewery_id_exists() throws ElementNotFoundException {
        //given
        when(breweryRepository.findById(ID)).thenReturn(Optional.ofNullable(singleSavedBrewery));

        //when
        service.deleteBreweryById(ID);

        //then
        verify(breweryRepository).delete(singleSavedBrewery);
    }

    @Test
    @DisplayName("Verify if ElementNotFoundException is thrown when brewery id does not exists during delete")
    void verify_update_brewery_by_brewery_id_when_brewery_id_do_not_exists_during_delete() {
        //then
        assertThrows(ElementNotFoundException.class, this::callDeleteBreweryByIdWhichDoesNotExist);
    }

    private void callDeleteBreweryByIdWhichDoesNotExist() throws ElementNotFoundException {
        //given
        when(breweryRepository.findById(ID)).thenReturn(Optional.empty());

        //when
        service.deleteBreweryById(ID);
    }


    @Test
    @DisplayName("Verify get brewery image from db base on brewery id")
    void verify_get_brewery_image_from_db_base_on_brewery_id() throws ElementNotFoundException {
        //given
        when(breweryRepository.findById(ID)).thenReturn(Optional.of(singleSavedBrewery));

        //when
        byte[] breweryImageFromDbBaseOnBreweryId = service.getBreweryImageFromDbBaseOnBreweryId(ID);

        //then
        assertEquals(singleSavedBrewery.getBreweryImage(), breweryImageFromDbBaseOnBreweryId);
    }

    @Test
    @DisplayName("Verify set brewery image to proper brewery base on brewery id")
    void verify_set_brewery_image_to_proper_brewery_base_on_brewery_id() throws ElementNotFoundException, IOException, InvalidImageParameters {
        //given
        byte[] byteArray = new byte[10];
        when(breweryRepository.findById(ID)).thenReturn(Optional.of(singleSavedBrewery));
        when(imageUpload.validateFile(file)).thenReturn(true);
        when(imageUpload.convertImageToByteArray(file)).thenReturn(byteArray);

        //when
        service.setBreweryImageToProperBreweryBaseOnBreweryId(ID, file);

        //then
        verify(breweryRepository).findById(ID);
    }

    @Test
    @DisplayName("Verify set image brewery by id when brewery id do not exists")
    void verify_set_image_brewery_by_id_when_brewery_id_do_not_exists() {
        //then
        assertThrows(ElementNotFoundException.class, this::callSetBreweryImageBreweryByIdWhichDoesNotExist);
    }

    private void callSetBreweryImageBreweryByIdWhichDoesNotExist() throws ElementNotFoundException, InvalidImageParameters, IOException {
        //given
        when(breweryRepository.findById(ID)).thenReturn(Optional.empty());

        //when
        service.setBreweryImageToProperBreweryBaseOnBreweryId(ID, file);
    }

    @Test
    @DisplayName("Verify set image brewery by id when brewery id do not exists")
    void verify_set_image_brewery_by_id_when_image_is_invalid() {
        //then
        assertThrows(InvalidImageParameters.class, this::callSetBreweryImageBreweryByIdWhichHasInvalidImage);
    }

    private void callSetBreweryImageBreweryByIdWhichHasInvalidImage() throws ElementNotFoundException, InvalidImageParameters, IOException {
        //given
        when(breweryRepository.findById(ID)).thenReturn(Optional.of(singleSavedBrewery));
        when(imageUpload.validateFile(file)).thenReturn(false);

        //when
        service.setBreweryImageToProperBreweryBaseOnBreweryId(ID, file);
    }
}
