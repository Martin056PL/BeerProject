package wawer.kamil.beerproject.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;
import wawer.kamil.beerproject.dto.BreweryDTO;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class BreweryControllerTestHelper {

    @Autowired
    private ObjectMapper objectMapper;

    private static ObjectMapper objectMapperStatic;

    @PostConstruct
    void init(){
        objectMapperStatic = objectMapper;
    }

    static List<BreweryDTO> getListOfBreweryDTO(MvcResult mvcResult) throws Exception {
        return objectMapperStatic.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>(){});
    }

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
