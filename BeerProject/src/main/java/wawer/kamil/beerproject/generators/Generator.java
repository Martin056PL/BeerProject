package wawer.kamil.beerproject.generators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.domain.Address;
import wawer.kamil.beerproject.domain.Beer;
import wawer.kamil.beerproject.domain.Brewery;
import wawer.kamil.beerproject.domain.enums.StyleBeer;
import wawer.kamil.beerproject.repositories.BreweryRepository;

@Component
@RequiredArgsConstructor
public class Generator {

    private final BreweryRepository repository;

    //@EventListener(ApplicationReadyEvent.class)
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

        Address address1 = Address.builder()
                .city("Poniatowa")
                .parcelNumber(43)
                .zipCode("24-320")
                .street("Przemyslowa")
                .build();

        brewery1.setAddress(address1);
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
                .email("browar@deerbear.pl")
                .website("http://deerbear.pl/")
                .phoneNumber(731533688L)
                .build();

        Address address2 = Address.builder()
                .city("Toruń")
                .localNumber(22)
                .parcelNumber(81)
                .zipCode("87-100")
                .street("Przemyslowa")
                .build();

        brewery2.setAddress(address2);

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
                .email("biuro@browarkormoran.pl")
                .website("https://browarkormoran.pl/")
                .phoneNumber(600305620L)
                .build();

        Address address3 = Address.builder()
                .city("Olsztyn")
                .parcelNumber(2)
                .zipCode("10-057")
                .street("Al. Gen. Wł. Sikorskiego")
                .build();

        brewery3.setAddress(address3);

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
                .email("kontakt@browardzikiwschod.pl")
                .website("https://browardzikiwschod.pl")
                .phoneNumber(506448234L)
                .build();

        Address address4 = Address.builder()
                .city("Lublin")
                .parcelNumber(7)
                .zipCode("20-474")
                .street("Smoluchowskiego")
                .build();

        brewery4.setAddress(address4);

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
                .email("pinta@browarpinta.pl")
                .website("http://www.browarpinta.pl/")
                .phoneNumber(514295723L)
                .build();

        Address address5 = Address.builder()
                .city("Żywiec")
                .parcelNumber(22)
                .zipCode("34-300")
                .street("Na Oklu")
                .build();

        brewery5.setAddress(address5);

        brewery5.addBeer(beer14);
        brewery5.addBeer(beer15);
        brewery5.addBeer(beer16);

        repository.save(brewery5);
    }
}
