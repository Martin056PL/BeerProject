package wawer.kamil.beerproject.service.impl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.model.Brewery;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BreweryServiceHelper {

    public static Page<Brewery> getBreweriesWithBeers(Page<Brewery> listOfBreweryPage, List<Beer> beersByBreweryId) {
        return listOfBreweryPage.map(mapBrewery(beersByBreweryId));
    }

    public static List<Brewery> getBreweriesWithBeers(List<Brewery> listOfBreweryPage, List<Beer> beersByBreweryId) {
        return listOfBreweryPage.stream().map(mapBrewery(beersByBreweryId)).collect(toList());
    }

    public static List<Long> getBreweriesIds(Page<Brewery> breweries) {
        return breweries.map(Brewery::getBreweryId).toList();
    }

    public static List<Long> getBreweriesIds(List<Brewery> breweries) {
        return breweries.stream().map(Brewery::getBreweryId).collect(toList());
    }

    private static Function<Brewery, Brewery> mapBrewery(List<Beer> beersByBreweryId) {
        return brewery ->
                Brewery.builder()
                        .breweryId(brewery.getBreweryId())
                        .name(brewery.getName())
                        .email(brewery.getEmail())
                        .phoneNumber(brewery.getPhoneNumber())
                        .website(brewery.getWebsite())
                        .address(brewery.getAddress())
                        .beerList(getBeerListForBrewery(beersByBreweryId, brewery))
                        .build();
    }

    public static List<Beer> getBeerListForBrewery(List<Beer> beersByBreweryId, Brewery bp) {
        return beersByBreweryId.stream().filter(matchBeerToBreweryByIdPredicate(bp)).collect(toList());
    }

    private static Predicate<Beer> matchBeerToBreweryByIdPredicate(Brewery bp) {
        return beer -> getBreweryId(beer).equals(bp.getBreweryId());
    }

    private static Long getBreweryId(Beer beer) {
        return beer.getBrewery().getBreweryId();
    }


}
