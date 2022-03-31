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
import wawer.kamil.beerproject.controllers.BreweryController;
import wawer.kamil.beerproject.dto.request.BreweryRequest;
import wawer.kamil.beerproject.dto.response.BreweryResponse;
import wawer.kamil.beerproject.service.BreweryService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static wawer.kamil.beerproject.helpers.BreweryTestHelper.getSingleBreweryRequest;
import static wawer.kamil.beerproject.helpers.BreweryTestHelper.getSingleBreweryResponse;

@ExtendWith(MockitoExtension.class)
class BreweryControllerAspectsTest {

    @Mock
    BreweryService service;

    @Mock
    Pageable pageable;

    @Mock
    MultipartFile multipartFile;

    BreweryController breweryControllerProxy;
    PrintStream old = System.out;
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    BreweryRequest breweryRequest;
    BreweryResponse breweryResponse;

    private static final Long ID = 1L;

    @BeforeEach
    void setUp() {
        this.breweryControllerProxy = configureBreweryControllerProxy();
        this.breweryRequest = getSingleBreweryRequest();
        this.breweryResponse = getSingleBreweryResponse();
        setCustomSetOut();
    }

    @AfterEach
    void cleanAfter() {
        setDefaultSetOut();
    }

    @Test
    @DisplayName("Should throw log when getAllBreweryPage is called with pageable param")
    void should_throw_log_when_getAllBreweryPage_is_called_with_id_with_param() {
        //when
        breweryControllerProxy.getAllBreweryPage(pageable);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(String.format("[main] DEBUG application.logger - Endpoint address: 'brewery' with GET method, request parameter - pageable: %s", pageable.toString()), removeDateTimeFromLog(testLog));
    }

    @Test
    @DisplayName("Should throw log when getAllBreweryList is called with pageable param")
    void should_throw_log_when_getAllBreweryList_is_called_with_id_with_param() {
        //when
        breweryControllerProxy.getAllBreweryList();
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals("[main] DEBUG application.logger - Endpoint address: 'brewery/list' with GET method", removeDateTimeFromLog(testLog));
    }

    @Test
    @DisplayName("Should throw log when getBreweryById is called with id param")
    void should_throw_log_when_getBreweryById_is_called_with_id_with_param() {
        //when
        breweryControllerProxy.getBreweryById(ID);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(String.format("[main] DEBUG application.logger - Endpoint address: 'brewery/{id}' with GET method, request parameter - id: %d", ID), removeDateTimeFromLog(testLog));
    }

    @Test
    @DisplayName("Should throw log when addNewBrewery is called with id param")
    void should_throw_log_when_addNewBrewery_is_called_with_id_with_param() throws URISyntaxException {
        //given
        when(service.createNewBrewery(breweryRequest)).thenReturn(breweryResponse);

        //when
        breweryControllerProxy.addNewBrewery(breweryRequest);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(String.format("[main] DEBUG application.logger - Endpoint address: 'brewery' with POST method, request parameter - brewery data: %s; %s; %s; %s; %s"
                , breweryRequest.getName()
                , breweryRequest.getEmail()
                , breweryRequest.getPhoneNumber()
                , breweryRequest.getAddress()
                , breweryRequest.getWebsite()), removeDateTimeFromLog(testLog));
    }

    @Test
    @DisplayName("Should throw log when updateBrewery is called with id param")
    void should_throw_log_when_updateBrewery_is_called_with_id_with_param() {
        //when
        breweryControllerProxy.updateBrewery(ID, breweryRequest);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(String.format("[main] DEBUG application.logger - Endpoint address: 'brewery/{id}' with PUT method, request parameter - brewery id: %s;  brewery data: %s; %s; %s; %s; %s"
                , ID
                , breweryRequest.getName()
                , breweryRequest.getEmail()
                , breweryRequest.getPhoneNumber()
                , breweryRequest.getAddress()
                , breweryRequest.getWebsite()), removeDateTimeFromLog(testLog));
    }

    @Test
    @DisplayName("Should throw log when deleteBrewery is called with id param")
    void should_throw_log_when_deleteBrewery_is_called_with_id_with_param() {
        //when
        breweryControllerProxy.deleteBrewery(ID);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(String.format("[main] DEBUG application.logger - Endpoint address: 'brewery/{id}' with DELETE method, request parameter - id: %d", ID), removeDateTimeFromLog(testLog));
    }

    @Test
    @DisplayName("Should throw log when uploadImage is called with id param")
    void should_throw_log_when_uploadImage_is_called_with_id_with_param() {
        //when
        breweryControllerProxy.uploadImage(ID, multipartFile);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(String.format("[main] DEBUG application.logger - Endpoint address: 'brewery/{id}/image' with POST method, request parameter - id: %s, multipartFile: name: %s, contentType: %s , size: %s"
                , ID
                , multipartFile.getName()
                , multipartFile.getContentType()
                , multipartFile.getSize()), removeDateTimeFromLog(testLog));
    }

    private BreweryController configureBreweryControllerProxy() {
        BreweryController breweryController = new BreweryController(service);
        AspectJProxyFactory factory = new AspectJProxyFactory(breweryController);
        factory.setTarget(breweryController);
        factory.addAspect(BreweryControllerAspects.class);
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
