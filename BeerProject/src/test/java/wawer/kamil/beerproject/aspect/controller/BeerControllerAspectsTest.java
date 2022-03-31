package wawer.kamil.beerproject.aspect.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.controllers.BeerController;
import wawer.kamil.beerproject.dto.request.BeerRequest;
import wawer.kamil.beerproject.dto.response.BeerResponse;
import wawer.kamil.beerproject.service.BeerService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static wawer.kamil.beerproject.helpers.BeerTestHelper.getBeerRequest;
import static wawer.kamil.beerproject.helpers.BeerTestHelper.getBeerResponse;

@ExtendWith(MockitoExtension.class)
class BeerControllerAspectsTest {

    @Mock
    BeerService service;

    @Mock
    Pageable pageable;

    @Mock
    MultipartFile multipartFile;

    BeerController beerControllerProxy;
    PrintStream old = System.out;
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    BeerRequest beerRequest;
    BeerResponse beerResponse;

    private static final Long ID = 1L;

    @BeforeEach
    void setUp() {
        this.beerControllerProxy = configureBeerControllerProxy();
        this.beerRequest = getBeerRequest();
        this.beerResponse = getBeerResponse();
        setCustomSetOut();
    }

    @AfterEach
    void cleanAfter() {
        setDefaultSetOut();
    }

    @Test
    @DisplayName("Should throw log when findAllBeersPage is called with pageable param")
    void should_throw_log_when_findAllBeersPage_is_called_with_id_with_param() {
        //when
        beerControllerProxy.findAllBeersPage(pageable);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                String.format("[main] DEBUG application.logger - Endpoint address: 'beer' with GET method, request parameter - pageable: %s", pageable.toString()),
                removeDateTimeFromLog(testLog)
        );
    }

    @Test
    @DisplayName("Should throw log when findAllBeersList is called with pageable param")
    void should_throw_log_when_findAllBeersList_is_called_with_id_with_param() {
        //when
        beerControllerProxy.findAllBeersList();
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                "[main] DEBUG application.logger - Endpoint address: 'beer/list' with GET method",
                removeDateTimeFromLog(testLog)
        );
    }

    @Test
    @DisplayName("Should throw log when findAllBeersByBreweryIdPage is called with id param")
    void should_throw_log_when_findAllBeersByBreweryIdPage_is_called_with_id_with_param() {
        //when
        beerControllerProxy.findAllBeersByBreweryIdPage(ID, pageable);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                String.format("[main] DEBUG application.logger - Endpoint address: 'brewery/{breweryId}/beer' with GET method, request parameter - breweryId: %d; Pageable: %s", ID, pageable),
                removeDateTimeFromLog(testLog)
        );
    }

    @Test
    @DisplayName("Should throw log when findAllBeersByBreweryIdPage is called with id param")
    void should_throw_log_when_findAllBeersByBreweryIdList_is_called_with_id_with_param() {
        //when
        beerControllerProxy.findAllBeersByBreweryIdList(ID);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                String.format("[main] DEBUG application.logger - Endpoint address: 'brewery/{breweryId}/beer/list' with GET method, request parameter - breweryId: %s", ID),
                removeDateTimeFromLog(testLog)
        );
    }

    @Test
    @DisplayName("Should throw log when findProperBeerByBeerId is called with id param")
    void should_throw_log_when_findProperBeerByBeerId_is_called_with_id_with_param() {
        //when
        beerControllerProxy.findProperBeerByBeerId(ID);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                String.format("[main] DEBUG application.logger - Endpoint address: 'beer/{beerId}' with GET method, request parameter - beerId: %d", ID),
                removeDateTimeFromLog(testLog)
        );
    }


    @Test
    @DisplayName("Should throw log when addBeerToBreweryByBreweryId is called with id param")
    void should_throw_log_when_addBeerToBreweryByBreweryId_is_called_with_id_with_param() throws URISyntaxException {
        //given
        when(service.addNewBeerAssignedToBreweryByBreweryId(ID, beerRequest)).thenReturn(beerResponse);

        //when
        beerControllerProxy.addBeerToBreweryByBreweryId(ID, beerRequest);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                String.format("[main] DEBUG application.logger - Endpoint address: 'beer' with POST method, request parameter -  beerData: Name: %s; Style: %s; Alcohol: %s; Extract: %s"
                        , beerRequest.getName()
                        , beerRequest.getStyle()
                        , beerRequest.getAlcohol()
                        , beerRequest.getExtract()),
                removeDateTimeFromLog(testLog));
    }

    @Test
    @DisplayName("Should throw log when updateBeerBeerId is called with id param")
    void should_throw_log_when_updateBeerBeerId_is_called_with_id_with_param() {
        //when
        beerControllerProxy.updateBeerBeerId(ID, beerRequest);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                String.format("[main] DEBUG application.logger - Endpoint address: 'beer/{beerId}' with PUT method, request parameter - breweryId: %s, beerData: Name: %s; Style: %s; Alcohol: %s; Extract: %s"
                        , ID
                        , beerRequest.getName()
                        , beerRequest.getStyle()
                        , beerRequest.getAlcohol()
                        , beerRequest.getExtract()),
                removeDateTimeFromLog(testLog));
    }

    @Test
    @DisplayName("Should throw log when updateBeerBaseOnBreweryIdAndBeerId is called with id param")
    void should_throw_log_when_updateBeerBaseOnBreweryIdAndBeerId_is_called_with_id_with_param() {
        //when
        beerControllerProxy.updateBeerBaseOnBreweryIdAndBeerId(ID, ID, beerRequest);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                String.format("[main] DEBUG application.logger - Endpoint address: 'brewery/{breweryId}/beer/{beerId}' with PUT method, request parameter - breweryId: %s, beerId: %s beerData: Name: %s; Style: %s; Alcohol: %s; Extract: %s",
                        ID,
                        ID,
                        beerRequest.getName(),
                        beerRequest.getStyle(),
                        beerRequest.getAlcohol(),
                        beerRequest.getExtract()),
                removeDateTimeFromLog(testLog));
    }

    @Test
    @DisplayName("Should throw log when deleteBeerByBeerId is called with id param")
    void should_throw_log_when_deleteBeerByBeerId_is_called_with_id_with_param() {
        //when
        beerControllerProxy.deleteBeerByBeerId(ID);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                String.format("[main] DEBUG application.logger - Endpoint address: 'beer/{beerId}' with DELETE method, request parameter - beerId: %s", ID),
                removeDateTimeFromLog(testLog)
        );
    }

    @Test
    @DisplayName("Should throw log when downloadImage is called with id param")
    void should_throw_log_when_downloadImage_is_called_with_id_with_param() {
        //when
        beerControllerProxy.downloadImage(ID);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                String.format("[main] DEBUG application.logger - Endpoint address: 'brewery/{breweryId}/beer/{beerId}/image' with GET method, request parameter - beerId: %s", ID),
                removeDateTimeFromLog(testLog)
        );
    }

    @Test
    @DisplayName("Should throw log when downloadImage is called with id param")
    void should_throw_log_when_uploadImage_is_called_with_id_with_param() {
        //when
        beerControllerProxy.uploadImage(ID, multipartFile);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                String.format("[main] DEBUG application.logger - Endpoint address: 'brewery/{breweryId}/beer/{beerId}/image' with POST method, request parameter - beerId: %s, multipartFile: name: %s, contentType: %s , size: %s",
                        ID,
                        multipartFile.getName(),
                        multipartFile.getContentType(),
                        multipartFile.getSize()),
                removeDateTimeFromLog(testLog)
        );
    }

    private BeerController configureBeerControllerProxy() {
        BeerController beerController = new BeerController(service);
        AspectJProxyFactory factory = new AspectJProxyFactory(beerController);
        factory.setTarget(beerController);
        factory.addAspect(BeerControllerAspects.class);
        return factory.getProxy();
    }

    private void setCustomSetOut() {
        System.setOut(new PrintStream(byteArrayOutputStream));
    }

    private void setDefaultSetOut() {
        System.out.flush();
        System.setOut(old);
    }

    private String removeDateTimeFromLog(String originalLog) {
        int indexOfLogBeginning = 13;
        return originalLog.substring(indexOfLogBeginning).trim();
    }

}
