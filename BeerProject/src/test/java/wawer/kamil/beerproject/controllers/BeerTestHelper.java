package wawer.kamil.beerproject.controllers;

import wawer.kamil.beerproject.model.Beer;

import java.util.List;

import static wawer.kamil.beerproject.controllers.BreweryTestHelper.getSingleSavedBrewery;

public class BeerTestHelper {

    public static List<Beer> getListOfBearsBaseOnIDs() {
        return List.of(Beer.builder()
                .beerId(1L)
                .brewery(getSingleSavedBrewery())
                .name("Testowe Piwo")
                .style("Testoy styl")
                .alcohol(5.6)
                .extract(20.0)
                .build());
    }
}
