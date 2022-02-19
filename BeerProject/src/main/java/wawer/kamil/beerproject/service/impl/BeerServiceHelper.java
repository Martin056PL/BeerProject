package wawer.kamil.beerproject.service.impl;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wawer.kamil.beerproject.model.Beer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeerServiceHelper {

    public static void mapBeerProperties(Beer beerToUpdate, Beer updatedBeer) {
        beerToUpdate.setName(updatedBeer.getName());
        beerToUpdate.setExtract(updatedBeer.getExtract());
        beerToUpdate.setStyle(updatedBeer.getStyle());
        beerToUpdate.setAlcohol(updatedBeer.getAlcohol());
    }

}
