package wawer.kamil.beerproject.aspect.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.dto.response.BeerResponse;

import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j(topic = "application.logger")
public class BeerServiceAspects {

    private static final String BEER_LOG = "List of returned beerId: {}";

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.BeerServiceImpl.findAllBeersPage(..))", returning = "beerResponsePage")
    public void logFindAllBeersPage(Page<BeerResponse> beerResponsePage) {
        log.debug(BEER_LOG, beerResponsePage.stream().map(BeerResponse::getId).collect(Collectors.toList()));
    }

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.BeerServiceImpl.findAllBeersList(..))", returning = "beerResponsePage")
    public void logFindAllBeersList(List<BeerResponse> beerResponsePage) {
        log.debug(BEER_LOG, beerResponsePage.stream().map(BeerResponse::getId).collect(Collectors.toList()));
    }

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.BeerServiceImpl.findBeerById(..))", returning = "beerResponse")
    public void logFindBeerById(BeerResponse beerResponse) {
        log.debug(BEER_LOG, beerResponse.getId());
    }

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.BeerServiceImpl.findAllBeersByBreweryIdPage(..))", returning = "beerResponsePage")
    public void logGetAllBreweryPage(Page<BeerResponse> beerResponsePage) {
        log.debug(BEER_LOG, beerResponsePage.stream().map(BeerResponse::getId).collect(Collectors.toList()));
    }

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.BeerServiceImpl.findAllBeersByBreweryIdList(..))", returning = "beerResponseList")
    public void logGetAllBreweryList(List<BeerResponse> beerResponseList) {
        log.debug("List of returned Id: {}", beerResponseList.stream().map(BeerResponse::getId).collect(Collectors.toList()));
    }

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.BeerServiceImpl.addNewBeerAssignedToBreweryByBreweryId(..))", returning = "beerResponse")
    public void logAddNewBeerAssignedToBreweryByBreweryId(BeerResponse beerResponse) {
        log.debug("Add new beer with Id: {}", beerResponse.getId());
    }

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.BeerServiceImpl.updateBeerByBeerId(..))", returning = "beerResponse")
    public void logUpdateBeerByBeerId(BeerResponse beerResponse) {
        log.debug("Updated beer with Id: {}", beerResponse.getId());
    }

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.BeerServiceImpl.updateBeerByBreweryIdAndBeerId(..))", returning = "beerResponse")
    public void logUpdateBeerByBreweryIdAndBeerId(BeerResponse beerResponse) {
        log.debug("Updated beer with Id: {}", beerResponse.getId());
    }


}
