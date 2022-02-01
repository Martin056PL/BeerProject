package wawer.kamil.beerproject.controllers;

public class BreweryControllerTestHelper {

    static String breweryContent() {
        return "{" +
                "\"name\": \"Browar Testowy\"," +
                "\"email\": \"test@test.pl\"," +
                "\"phoneNumber\": 999000777," +
                "\"address\": {" +
                    "\"street\":\"Testowa\"," +
                    "\"parcelNumber\":69," +
                    "\"localNumber\":null," +
                    "\"zipCode\":\"69-666\"," +
                    "\"city\":\"Testowo\"" +
                "}," +
                "\"website\":\"http://testowy.pl\"" +
                "}";
    }
}
