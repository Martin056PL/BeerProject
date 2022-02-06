package wawer.kamil.beerproject.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import wawer.kamil.beerproject.dto.request.BreweryRequest;
import wawer.kamil.beerproject.dto.response.BreweryResponse;
import wawer.kamil.beerproject.model.Address;
import wawer.kamil.beerproject.model.Brewery;

import java.util.List;

public class BreweryControllerTestHelper {

    public static Page<Brewery> getBreweryPage() {
        return new PageImpl<>(List.of(getSingleSavedBrewery()));
    }

    public static Page<BreweryResponse> getBreweryResponsePage() {
        return new PageImpl<>(List.of(getSingleBreweryResponse()));
    }

    public static List<Brewery> getBreweryList() {
        return List.of(getSingleSavedBrewery());
    }

    public static List<BreweryResponse> getBreweryResponseList() {
        return List.of(getSingleBreweryResponse());
    }

    public static BreweryRequest getSingleBreweryRequest() {
        return BreweryRequest.builder()
                .name("Testowy")
                .email("testowy@gmail.com")
                .address(Address.builder()
                        .addressId(1L)
                        .street("Testowa")
                        .parcelNumber(1)
                        .localNumber(1)
                        .zipCode("20-200")
                        .city("Lublin")
                        .build()
                )
                .phoneNumber(123123123L)
                .website("www.testowa.pl")
                .build();
    }

    public static Brewery getSingleBreweryBeforeSave() {
        return Brewery.builder()
                .name("Testowy")
                .email("testowy@gmail.com")
                .address(Address.builder()
                        .addressId(1L)
                        .street("Testowa")
                        .parcelNumber(1)
                        .localNumber(1)
                        .zipCode("20-200")
                        .city("Lublin")
                        .build()
                )
                .phoneNumber(123123123L)
                .website("www.testowa.pl")
                .build();
    }

    public static Brewery getSingleSavedBrewery() {
        return Brewery.builder()
                .breweryId(1L)
                .name("Testowy")
                .email("testowy@gmail.com")
                .address(Address.builder()
                        .addressId(1L)
                        .street("Testowa")
                        .parcelNumber(1)
                        .localNumber(1)
                        .zipCode("20-200")
                        .city("Lublin")
                        .build()
                )
                .phoneNumber(123123123L)
                .website("www.testowa.pl")
                .build();
    }

    public static BreweryResponse getSingleBreweryResponse() {
        return BreweryResponse.builder()
                .id(1L)
                .name("Testowy")
                .email("testowy@gmail.com")
                .address(Address.builder()
                        .addressId(1L)
                        .street("Testowa")
                        .parcelNumber(1)
                        .localNumber(1)
                        .zipCode("20-200")
                        .city("Lublin")
                        .build()
                )
                .phoneNumber(123123123L)
                .website("www.testowa.pl")
                .build();
    }

    public static BreweryRequest getUpdatedSingleBreweryRequest() {
        return BreweryRequest.builder()
                .name("NowyTest")
                .email("NowyTest@gmail.com")
                .address(Address.builder()
                        .addressId(1L)
                        .street("NowyTest")
                        .parcelNumber(1)
                        .localNumber(1)
                        .zipCode("55-555")
                        .city("NowyTest")
                        .build()
                )
                .phoneNumber(789567345L)
                .website("www.NowyTest.pl")
                .build();
    }

    public static Brewery getUpdatedSingleBreweryBeforeSave() {
        return Brewery.builder()
                .name("NowyTest")
                .email("NowyTest@gmail.com")
                .address(Address.builder()
                        .addressId(1L)
                        .street("NowyTest")
                        .parcelNumber(1)
                        .localNumber(1)
                        .zipCode("55-555")
                        .city("NowyTest")
                        .build()
                )
                .phoneNumber(789567345L)
                .website("www.NowyTest.pl")
                .build();
    }

    public static Brewery getUpdatedSingleBreweryAfterSave() {
        return Brewery.builder()
                .breweryId(1L)
                .name("NowyTest")
                .email("NowyTest@gmail.com")
                .address(Address.builder()
                        .addressId(1L)
                        .street("NowyTest")
                        .parcelNumber(1)
                        .localNumber(1)
                        .zipCode("55-555")
                        .city("NowyTest")
                        .build()
                )
                .phoneNumber(789567345L)
                .website("www.NowyTest.pl")
                .build();
    }

    public static BreweryResponse getUpdatedSingleBreweryResponseAfterSave() {
        return BreweryResponse.builder()
                .id(1L)
                .name("NowyTest")
                .email("NowyTest@gmail.com")
                .address(Address.builder()
                        .addressId(1L)
                        .street("NowyTest")
                        .parcelNumber(1)
                        .localNumber(1)
                        .zipCode("55-555")
                        .city("NowyTest")
                        .build()
                )
                .phoneNumber(789567345L)
                .website("www.NowyTest.pl")
                .build();
    }

    public static byte[] newArray() {
        return new byte[10];
    }


}
