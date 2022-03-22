package wawer.kamil.beerproject.service.impl;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import wawer.kamil.beerproject.model.Beer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeerServiceHelper {

    public static void mapBeerProperties(Beer beerToUpdate, Beer beerFromRequest) {
        beerToUpdate.setName(beerFromRequest.getName());
        beerToUpdate.setExtract(beerFromRequest.getExtract());
        beerToUpdate.setStyle(beerFromRequest.getStyle());
        beerToUpdate.setAlcohol(beerFromRequest.getAlcohol());
    }

}
