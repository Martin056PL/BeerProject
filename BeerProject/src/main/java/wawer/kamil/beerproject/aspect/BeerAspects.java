package wawer.kamil.beerproject.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.dto.BeerDTO;

@Aspect
@Component
@Slf4j(topic = "application.logger")
public class BeerAspects {

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BeerController.findAllBeersPage(..)) && args(pageable)")
    public void logFindAllBeersPage(Pageable pageable) {
        log.debug("Endpoint address: 'beer' with GET method, request parameter - pageable: {}", pageable);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BeerController.findAllBeersList(..))")
    public void logFindAllBeersList() {
        log.debug("Endpoint address: 'beer/list' with GET method");
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BeerController.findAllBeersByBreweryIdPage(..)) && args(breweryId, pageable)", argNames = "breweryId,pageable")
    public void logFindAllBeersByBreweryIdPage(Long breweryId, Pageable pageable) {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer' with GET method, request parameter - breweryId: {}; Pageable: {}", breweryId, pageable);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BeerController.findAllBeersByBreweryIdList(..)) && args(breweryId)")
    public void logFindAllBeersByBreweryIdList(Long breweryId) {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer/list' with GET method, request parameter - breweryId: {}", breweryId);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BeerController.findProperBeerByBeerId(..)) && args(beerId)")
    public void logFindProperBeerByBeerId(Long beerId) {
        log.debug("Endpoint address: 'beer/{beerId}' with GET method, request parameter - beerId: {}", beerId);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BeerController.findProperBeerBaseOnBreweryIdAndBeerId(..)) && args(breweryId, beerId)", argNames = "breweryId,beerId")
    public void logFindProperBeerByBeerId(Long breweryId, Long beerId) {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer/{beerId}' with GET method, request parameter - breweryId: {}, beerId: {}", breweryId, beerId);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BeerController.addNewBeer(..)) && args(beerDTO)")
    public void logAddNewBeer(BeerDTO beerDTO) {
        log.debug("Endpoint address: 'beer' with POST method, request parameter -  beerData: Name: {}; Style: {}; Alcohol: {}; Extract: {}"
                , beerDTO.getName()
                , beerDTO.getStyle()
                , beerDTO.getAlcohol()
                , beerDTO.getExtract());
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BeerController.addNewBeerAssignedToBreweryByBreweryId(..)) && args(breweryId, beerDTO)", argNames = "breweryId,beerDTO")
    public void logAddNewBeerAssignedToBreweryByBreweryId(Long breweryId, BeerDTO beerDTO) {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer' with POST method, request parameter - breweryId: {}, beerData: Name: {}; Style: {}; Alcohol: {}; Extract: {}"
                , breweryId
                , beerDTO.getName()
                , beerDTO.getStyle()
                , beerDTO.getAlcohol()
                , beerDTO.getExtract());
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BeerController.updateBeer(..)) && args(beerId, beerDTO)", argNames = "beerId,beerDTO")
    public void logUpdateBeer(Long beerId, BeerDTO beerDTO) {
        log.debug("Endpoint address: 'beer/{beerId}' with PUT method, request parameter - breweryId: {}, beerData: Name: {}; Style: {}; Alcohol: {}; Extract: {}"
                , beerId
                , beerDTO.getName()
                , beerDTO.getStyle()
                , beerDTO.getAlcohol()
                , beerDTO.getExtract());
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BeerController.updateBeerBaseOnBreweryIdAndBeerId(..)) && args(breweryId, beerId, beerDTO)", argNames = "breweryId,beerId,beerDTO")
    public void logUpdateBeerBaseOnBreweryIdAndBeerId(Long breweryId, Long beerId, BeerDTO beerDTO) {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer/{beerId}' with PUT method, request parameter - breweryId: {}, beerId: {} beerData: Name: {}; Style: {}; Alcohol: {}; Extract: {}"
                , breweryId
                , beerId
                , beerDTO.getName()
                , beerDTO.getStyle()
                , beerDTO.getAlcohol()
                , beerDTO.getExtract());
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BeerController.deleteBeerByBeerId(..)) && args(beerId)")
    public void logDeleteBeerByBeerId(Long beerId) {
        log.debug("Endpoint address: 'beer/{beerId}' with DELETE method, request parameter - beerId: {}", beerId);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BeerController.deleteBeerByBreweryIdAndBeerId(..)) && args(breweryId, beerId)", argNames = "breweryId,beerId")
    public void logDeleteBeerByBreweryIdAndBeerId(Long breweryId, Long beerId) {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer/{beerId}' with DELETE method, request parameter - breweryId: {}, beerId: {}", breweryId, beerId);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BeerController.uploadImage(..)) && args(breweryId, beerId)", argNames = "breweryId,beerId")
    public void logUploadImage(Long breweryId, Long beerId) {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer/{beerId}/image' with POST method, request parameter - breweryId: {}, beerId: {}", breweryId, beerId);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.BeerController.downloadImage(..)) && args(breweryId, beerId)", argNames = "breweryId,beerId")
    public void logDownloadImage(Long breweryId, Long beerId) {
        log.debug("Endpoint address: 'brewery/{breweryId}/beer/{beerId}/image' with GET method, request parameter - breweryId: {}, beerId: {}", breweryId, beerId);
    }

}
