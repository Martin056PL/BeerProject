package wawer.kamil.beerproject.aspect.controller;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.dto.BreweryDTO;

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

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BreweryController.getBreweryByBreweryId(..)) && args(id)")
    public void logGetBreweryByBreweryId(Long id) {
        log.debug("Endpoint address: 'brewery/{breweryId}' with GET method, request parameter - id: {}", id);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BreweryController.addNewBrewery(..)) && args(breweryDTO)")
    public void logAddNewBrewery(BreweryDTO breweryDTO) {
        log.debug("Endpoint address: 'brewery' with POST method, request parameter - brewery data: {}; {}; {}; {}"
                , breweryDTO.getName()
                , breweryDTO.getWebsite()
                , breweryDTO.getEmail()
                , breweryDTO.getPhoneNumber());
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BreweryController.updateBrewery(..)) && args(breweryId, breweryDTO)", argNames = "breweryId,breweryDTO")
    public void logUpdateBrewery(Long breweryId, BreweryDTO breweryDTO) {
        log.debug("Endpoint address: 'brewery/{breweryId}' with PUT method, request parameter - brewery id: {};  brewery data: {}; {}; {}; {}"
                , breweryId
                , breweryDTO.getName()
                , breweryDTO.getWebsite()
                , breweryDTO.getEmail()
                , breweryDTO.getPhoneNumber());
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BreweryController.deleteBrewery(..)) && args(breweryId)")
    public void logDeleteBrewery(Long breweryId) {
        log.debug("Endpoint address: 'brewery/{breweryId}' with DELETE method, request parameter - id: {}", breweryId);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BreweryController.getBreweryByBreweryId(..)) && args(breweryId)")
    public void logUploadImage(Long breweryId) {
        log.debug("Endpoint address: 'brewery/{breweryId}/image' with POST method, request parameter - id: {}", breweryId);
    }

}