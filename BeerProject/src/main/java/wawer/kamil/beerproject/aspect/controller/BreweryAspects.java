package wawer.kamil.beerproject.aspect.controller;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import wawer.kamil.beerproject.dto.request.BreweryRequest;

@Aspect
@Component
@Slf4j(topic = "application.logger")
public class BreweryAspects {

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BreweryController.getAllBreweryPage(..)) && args(pageable)")
    public void logGetAllBreweryPage(Pageable pageable) {
        log.debug("Endpoint address: 'brewery' with GET method, request parameter - pageable: {}", pageable);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BreweryController.getAllBreweryList(..))")
    public void logGetAllBreweryList() {
        log.debug("Endpoint address: 'brewery/list' with GET method");
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BreweryController.getBreweryById(..)) && args(id)")
    public void logGetBreweryByBreweryId(Long id) {
        log.debug("Endpoint address: 'brewery/{id}' with GET method, request parameter - id: {}", id);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BreweryController.addNewBrewery(..)) && args(breweryRequest)")
    public void logAddNewBrewery(BreweryRequest breweryRequest) {
        log.debug("Endpoint address: 'brewery' with POST method, request parameter - brewery data: {}; {}; {}; {}; {}"
                , breweryRequest.getName()
                , breweryRequest.getEmail()
                , breweryRequest.getPhoneNumber()
                , breweryRequest.getAddress()
                , breweryRequest.getWebsite());
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BreweryController.updateBrewery(..)) && args(id, breweryRequest)", argNames = "id, breweryRequest")
    public void logUpdateBrewery(Long id, BreweryRequest breweryRequest) {
        log.debug("Endpoint address: 'brewery/{id}' with PUT method, request parameter - brewery id: {};  brewery data: {}; {}; {}; {}; {}"
                , id
                , breweryRequest.getName()
                , breweryRequest.getEmail()
                , breweryRequest.getPhoneNumber()
                , breweryRequest.getAddress()
                , breweryRequest.getWebsite());
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BreweryController.deleteBrewery(..)) && args(id)")
    public void logDeleteBrewery(Long id) {
        log.debug("Endpoint address: 'brewery/{id}' with DELETE method, request parameter - id: {}", id);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BreweryController.uploadImage(..)) && args(id, multipartFile)", argNames = "id, multipartFile")
    public void logUploadImage(Long id, MultipartFile multipartFile) {
        log.debug("Endpoint address: 'brewery/{id}/image' with POST method, request parameter - id: {}, multipartFile: name: {}, contentType: {} , size: {}"
                , id
                , multipartFile.getName()
                , multipartFile.getContentType()
                , multipartFile.getSize());
    }

}