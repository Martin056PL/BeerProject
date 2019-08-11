package wawer.kamil.beerproject.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.domain.Beer;
import wawer.kamil.beerproject.domain.Brewery;
import wawer.kamil.beerproject.domain.StyleBeer;
import wawer.kamil.beerproject.repositories.BreweryRepository;

@Component
@RequiredArgsConstructor
public class Generator {

    private final BreweryRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    public void addData(){

        Beer beer = Beer.builder()
                .alcohol(5.2)
                .extract(12)
                .name("Porter")
                .style(StyleBeer.AmericanPorter.getStyle())
                .build();

        Beer beer1 = Beer.builder()
                .alcohol(6.0)
                .extract(14)
                .name("IPA")
                .style(StyleBeer.EnglishIPA.getStyle())
                .build();

        Brewery brewery = Brewery.builder()
                .name("Browar Zakładowy")
                .address("Poniatowa")
                .email("www.zakladowy@.gamil.pl")
                .website("www.zakladowy.pl")
                .phoneNumber(123123123L)
                .build();

        brewery.addBeer(beer);
        brewery.addBeer(beer1);

        repository.save(brewery);

    }
}
