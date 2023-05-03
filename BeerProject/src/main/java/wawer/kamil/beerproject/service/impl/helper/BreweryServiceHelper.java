package wawer.kamil.beerproject.service.impl.helper;

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
        return breweries.map(Brewery::getId).toList();
    }

    public static List<Long> getBreweriesIds(List<Brewery> breweries) {
        return breweries.stream().map(Brewery::getId).collect(toList());
    }

    private static Function<Brewery, Brewery> mapBrewery(List<Beer> beersByBreweryId) {
        return brewery ->
                Brewery.builder()
                        .id(brewery.getId())
                        .name(brewery.getName())
                        .email(brewery.getEmail())
                        .phoneNumber(brewery.getPhoneNumber())
                        .website(brewery.getWebsite())
                        .beerList(getBeerListForBrewery(beersByBreweryId, brewery))
                        .build();
    }

    public static List<Beer> getBeerListForBrewery(List<Beer> beersByBreweryId, Brewery bp) {
        return beersByBreweryId.stream().filter(matchBeerToBreweryByIdPredicate(bp)).collect(toList());
    }

    private static Predicate<Beer> matchBeerToBreweryByIdPredicate(Brewery bp) {
        return beer -> getBreweryId(beer).equals(bp.getId());
    }

    private static Long getBreweryId(Beer beer) {
        return beer.getBrewery().getId();
    }

    public static void mapBreweryProperties(Brewery breweryToUpdate, Brewery breweryFromRequest) {
        breweryToUpdate.setName(breweryFromRequest.getName());
        breweryToUpdate.setEmail(breweryFromRequest.getEmail());
        breweryToUpdate.setPhoneNumber(breweryFromRequest.getPhoneNumber());
        breweryToUpdate.setWebsite(breweryFromRequest.getWebsite());
    }

}
