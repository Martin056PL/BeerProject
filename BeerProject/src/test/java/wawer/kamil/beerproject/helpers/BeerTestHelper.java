package wawer.kamil.beerproject.helpers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import wawer.kamil.beerproject.dto.request.BeerRequest;
import wawer.kamil.beerproject.dto.response.BeerResponse;
import wawer.kamil.beerproject.model.Beer;

import java.util.List;

import static wawer.kamil.beerproject.helpers.BreweryTestHelper.getSingleBrewery;

public class BeerTestHelper {

    public static Page<Beer> getBeerPage() {
        return new PageImpl(getListOfBeers());
    }

    public static Page<BeerResponse> getBeerResponsesPage() {
        return new PageImpl(getListOfBeers());
    }

    public static List<Beer> getListOfBeers() {
        return List.of(getBeer());
    }

    public static List<BeerResponse> getListOfBeersResponses() {
        return List.of(getBeerResponse());
    }

    public static BeerRequest getBeerRequest() {
        return BeerRequest.builder()
                .name("Testowe Piwo")
                .style("Testoy styl")
                .alcohol(5.6)
                .extract(20.0)
                .build();
    }

    public static Beer getBeerBeforeUpdate() {
        return Beer.builder()
                .beerId(1L)
                .name("Testowe Piwo")
                .style("Testoy styl")
                .alcohol(5.6)
                .extract(20.0)
                .build();
    }

    public static Beer getBeer() {
        return Beer.builder()
                .beerId(1L)
                .brewery(getSingleBrewery())
                .name("Testowe Piwo")
                .style("Testoy styl")
                .alcohol(5.6)
                .extract(20.0)
                .build();
    }

    public static Beer getUpdatedBeer() {
        return Beer.builder()
                .beerId(1L)
                .brewery(getSingleBrewery())
                .name("Nowe piwo testowe")
                .style("Nowy testowy styl")
                .alcohol(7.0)
                .extract(10.0)
                .build();
    }

    public static BeerResponse getBeerResponse() {
        return BeerResponse.builder()
                .id(1L)
                .name("Testowe Piwo")
                .style("Testoy styl")
                .alcohol(5.6)
                .extract(20.0)
                .build();
    }

    public static BeerResponse getUpdatedBeerResponse() {
        return BeerResponse.builder()
                .id(1L)
                .name("Nowe piwo testowe")
                .style("Nowy testowy styl")
                .alcohol(7.0)
                .extract(10.0)
                .build();
    }

    public static byte[] newArrayForBeerImage() {
        return new byte[10];
    }


}