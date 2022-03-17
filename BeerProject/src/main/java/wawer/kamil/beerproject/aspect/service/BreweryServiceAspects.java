package wawer.kamil.beerproject.aspect.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.dto.response.BreweryResponse;

import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j(topic = "application.logger")
public class BreweryServiceAspects {

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.BreweryServiceImpl.getAllBreweryPage(..))", returning = "breweryResponsePage")
    public void logGetAllBreweryPage(Page<BreweryResponse> breweryResponsePage) {
        log.debug("List of returned breweries with Ids: {}", breweryResponsePage.stream()
                .map(BreweryResponse::getId)
                .collect(Collectors.toList())
        );
    }

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.BreweryServiceImpl.getAllBreweryList(..))", returning = "breweryResponseList")
    public void logGetAllBreweryList(List<BreweryResponse> breweryResponseList) {
        log.debug("List of returned breweries with Ids: {}", breweryResponseList.stream()
                .map(BreweryResponse::getId)
                .collect(Collectors.toList())
        );
    }

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.BreweryServiceImpl.createNewBrewery(..))", returning = "savedBrewery")
    public void logCreateNewBrewery(BreweryResponse savedBrewery) {
        log.debug("Add new brewery with Id: {}", savedBrewery.getId());
    }

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.BreweryServiceImpl.updateBreweryById(..))", returning = "updatedBrewery")
    public void logUpdateBreweryById(BreweryResponse updatedBrewery) {
        log.debug("Updated brewery with Id: {}", updatedBrewery.getId());
    }
}
