package wawer.kamil.beerproject.service.impl;

import wawer.kamil.beerproject.dto.BreweryDTO;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.model.Brewery;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static wawer.kamil.beerproject.utils.mapper.BreweryMapper.mapBreweryDTOToBreweryEntity;

public class BreweryServiceHelper {

    private BreweryServiceHelper() {
    }

    public static List<Long> getBreweryIdList(List<BreweryDTO> breweryPageList) {
        return breweryPageList.stream()
                .map(BreweryDTO::getBreweryId)
                .collect(Collectors.toList());
    }

    public static List<Brewery> getBreweriesWithBeersPagedList(List<BreweryDTO> listOfBreweryPage, List<Beer> beersByBreweryId) {
        return listOfBreweryPage.stream().map(mapBreweryDTOToBreweryEntity(beersByBreweryId))
                .collect(Collectors.toList());
    }

    public static List<Beer> getBeerListForBrewery(List<Beer> beersByBreweryId, BreweryDTO bp) {
        return beersByBreweryId.stream().filter(matchBeerToBreweryByIdPredicate(bp)).collect(Collectors.toList());
    }

    private static Predicate<Beer> matchBeerToBreweryByIdPredicate(BreweryDTO bp) {
        return beer -> getBreweryId(beer).equals(bp.getBreweryId());
    }

    private static Long getBreweryId(Beer beer) {
        return beer.getBrewery().getBreweryId();
    }


}
