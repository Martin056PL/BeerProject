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

        Beer beer1 = Beer.builder()
                .alcohol(4.7)
                .extract(12.0)
                .name("Plazowicz")
                .style(StyleBeer.EuropeanFruitAndFlavoured.getStyle())
                .build();

        Beer beer2 = Beer.builder()
                .alcohol(4.4)
                .extract(12.0)
                .name("Jubilat")
                .style(StyleBeer.AmericanIndiaPaleAle.getStyle())
                .build();

        Beer beer3 = Beer.builder()
                .alcohol(5.0)
                .extract(13.0)
                .name("Sokowirowka")
                .style(StyleBeer.AmericanIndiaPaleAle.getStyle())
                .build();

        Beer beer4 = Beer.builder()
                .alcohol(9.7)
                .extract(24.0)
                .name("Pan Porter")
                .style(StyleBeer.AmericanPorter.getStyle())
                .build();

        Beer beer17 = Beer.builder()
                .alcohol(8.0)
                .extract(19.0)
                .name("600% normy")
                .style(StyleBeer.AmericanDoubleImperialIPA.getStyle())
                .build();

        Beer beer18 = Beer.builder()
                .alcohol(5.3)
                .extract(15.0)
                .name("Szybki strzał")
                .style(StyleBeer.EnglishPaleAle.getStyle())
                .build();

        Beer beer19 = Beer.builder()
                .alcohol(9.7)
                .extract(24.0)
                .name("Pan Porter")
                .style(StyleBeer.AmericanPorter.getStyle())
                .build();

        Beer beer20 = Beer.builder()
                .alcohol(9.0)
                .extract(24.0)
                .name("Pan Kierownik")
                .style(StyleBeer.AmericanStout.getStyle())
                .build();

        Beer beer21 = Beer.builder()
                .alcohol(6.6)
                .extract(18.0)
                .name("Zadymiarz")
                .style(StyleBeer.DunklerBock.getStyle())
                .build();

        Beer beer22 = Beer.builder()
                .alcohol(5.8)
                .extract(14.0)
                .name("Kolektyw")
                .style(StyleBeer.AmericanPorter.getStyle())
                .build();

        Brewery brewery1 = Brewery.builder()
                .name("Browar Zakładowy")
                .address("Przemysłowa 43, 24-320 Poniatowa")
                .email("kontakt@browarzakladowy.pl")
                .website("http://browarzakladowy.pl")
                .phoneNumber(123123123L)
                .build();

        brewery1.addBeer(beer1);
        brewery1.addBeer(beer2);
        brewery1.addBeer(beer3);
        brewery1.addBeer(beer4);
        brewery1.addBeer(beer17);
        brewery1.addBeer(beer18);
        brewery1.addBeer(beer19);
        brewery1.addBeer(beer20);
        brewery1.addBeer(beer21);
        brewery1.addBeer(beer22);


        repository.save(brewery1);

        Beer beer5 = Beer.builder()
                .alcohol(6.5)
                .extract(16.0)
                .name("Coffee Break")
                .style(StyleBeer.AmericanStout.getStyle())
                .build();

        Beer beer6 = Beer.builder()
                .alcohol(6.2)
                .extract(16.0)
                .name("Hazy Dreamer")
                .style(StyleBeer.EnglishIPA.getStyle())
                .build();

        Beer beer7 = Beer.builder()
                .alcohol(4.6)
                .extract(12.5)
                .name("Let’s Cook")
                .style(StyleBeer.Gose.getStyle())
                .build();

        Brewery brewery2 = Brewery.builder()
                .name("Deer Bear")
                .address("Kościuszki 81 lok. 22, 87-100 Toruń")
                .email("browar@deerbear.pl")
                .website("http://deerbear.pl/")
                .phoneNumber(731533688L)
                .build();

        brewery2.addBeer(beer5);
        brewery2.addBeer(beer6);
        brewery2.addBeer(beer7);

        repository.save(brewery2);

        Beer beer8 = Beer.builder()
                .alcohol(4.5)
                .extract(12.0)
                .name("Kormoran Ciemny")
                .style(StyleBeer.AmericanDarkLager.getStyle())
                .build();

        Beer beer9 = Beer.builder()
                .alcohol(5.1)
                .extract(12.0)
                .name("Orkiszowe")
                .style(StyleBeer.EnglishIPA.getStyle())
                .build();

        Beer beer10 = Beer.builder()
                .alcohol(8.5)
                .extract(23.5)
                .name("Porter Warminski z Wisnią")
                .style(StyleBeer.EnglishPorter.getStyle())
                .build();

        Brewery brewery3 = Brewery.builder()
                .name("Kormoran")
                .address("Al. Gen. Wł. Sikorskiego 2 10-057 Olsztyn")
                .email("biuro@browarkormoran.pl")
                .website("https://browarkormoran.pl/")
                .phoneNumber(600305620L)
                .build();

        brewery3.addBeer(beer8);
        brewery3.addBeer(beer9);
        brewery3.addBeer(beer10);

        repository.save(brewery3);

        Beer beer11 = Beer.builder()
                .alcohol(6.5)
                .extract(16.0)
                .name("Czarny Jastrzab")
                .style(StyleBeer.AmericanBlackIPA.getStyle())
                .build();

        Beer beer12 = Beer.builder()
                .alcohol(5.0)
                .extract(12.0)
                .name("SŁOŃCE PRERII")
                .style(StyleBeer.CreamAle.getStyle())
                .build();

        Beer beer13 = Beer.builder()
                .alcohol(8.5)
                .extract(18.5)
                .name("HOP RUNNER")
                .style(StyleBeer.AmericanDoubleImperialIPA.getStyle())
                .build();

        Brewery brewery4 = Brewery.builder()
                .name("Dziki wschod")
                .address("Smoluchowskiego 7 20-474 Lublin")
                .email("kontakt@browardzikiwschod.pl")
                .website("https://browardzikiwschod.pl")
                .phoneNumber(506448234L)
                .build();

        brewery4.addBeer(beer11);
        brewery4.addBeer(beer12);
        brewery4.addBeer(beer13);

        repository.save(brewery4);

        Beer beer14 = Beer.builder()
                .alcohol(6.6)
                .extract(15.5)
                .name("PINTA Hoplaaga")
                .style(StyleBeer.AmericanIndiaPaleAle.getStyle())
                .build();

        Beer beer15 = Beer.builder()
                .alcohol(6.1)
                .extract(15.5)
                .name("PINTA Vermont IPA")
                .style(StyleBeer.AmericanIndiaPaleAle.getStyle())
                .build();

        Beer beer16 = Beer.builder()
                .alcohol(4.1)
                .extract(10.5)
                .name("PINTA Pierwsza Pomoc")
                .style(StyleBeer.EuropeanPilsner.getStyle())
                .build();

        Brewery brewery5 = Brewery.builder()
                .name("Pinta")
                .address("Na Oklu 22, 34-300 Żywiec ")
                .email("pinta@browarpinta.pl")
                .website("http://www.browarpinta.pl/")
                .phoneNumber(514295723L)
                .build();

        brewery5.addBeer(beer14);
        brewery5.addBeer(beer15);
        brewery5.addBeer(beer16);

        repository.save(brewery5);
    }
}
