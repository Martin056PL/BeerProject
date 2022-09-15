package wawer.kamil.beerproject.utils.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import wawer.kamil.beerproject.dto.request.BreweryRequest;
import wawer.kamil.beerproject.dto.response.BreweryResponse;
import wawer.kamil.beerproject.model.Brewery;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static wawer.kamil.beerproject.helpers.BreweryTestHelper.*;

class BreweryMapperTest {

    private final EntityMapper<Brewery, BreweryRequest, BreweryResponse> mapper = new EntityMapper<>(Brewery.class, BreweryResponse.class);
    private Brewery brewery;
    private BreweryRequest breweryRequest;
    private List<Brewery> breweryList;
    private Page<Brewery> breweryPage;

    @BeforeEach
    void setUp(){
        this.brewery = getSingleBrewery();
        this.breweryList = getBreweryList();
        this.breweryRequest = getSingleBreweryRequest();
        this.breweryPage = getBreweryPage();
    }

    @Test
    @DisplayName("Should map brewery to BreweryResponse keeping all values the same")
    void should_map_brewery_to_brewery_response_keeping_all_values_the_same() {
        //when
        BreweryResponse breweryResponse = mapper.mapEntityToResponse(brewery);

        //then
        assertEquals(brewery.getBreweryId(), breweryResponse.getId());
        assertEquals(brewery.getName(), breweryResponse.getName());
        assertEquals(brewery.getEmail(), breweryResponse.getEmail());
        assertEquals(brewery.getAddress().getStreet(), breweryResponse.getAddress().getStreet());
        assertEquals(brewery.getAddress().getLocalNumber(), breweryResponse.getAddress().getLocalNumber());
        assertEquals(brewery.getAddress().getZipCode(), breweryResponse.getAddress().getZipCode());
        assertEquals(brewery.getAddress().getParcelNumber(), breweryResponse.getAddress().getParcelNumber());
        assertEquals(brewery.getPhoneNumber(), breweryResponse.getPhoneNumber());
        assertEquals(brewery.getWebsite(), breweryResponse.getWebsite());
        assertEquals(brewery.getBeerList(), breweryResponse.getBeerList());
    }

    @Test
    @DisplayName("Should map List<Brewery> to List<BreweryResponse> keeping all values the same")
    void should_map_brewery_list_to_brewery_response_list_keeping_all_values_the_same() {
        //when
        List<BreweryResponse> breweryResponseList = mapper.mapEntitiesToEntitiesResponse(breweryList);

        //then
        assertEquals(breweryList.size(), breweryResponseList.size());
        assertEquals(breweryList.get(0).getBreweryId(), breweryResponseList.get(0).getId());
    }

    @Test
    @DisplayName("Should map Page<Brewery> to Page<BreweryResponse> keeping all values the same")
    void should_map_brewery_page_to_brewery_response_page_keeping_all_values_the_same() {
        //when
        Page<BreweryResponse> breweryResponsePage = mapper.mapEntityPageToResponsePage(breweryPage);

        //then
        assertEquals(breweryPage.getTotalPages(), breweryResponsePage.getTotalPages());
        assertEquals(breweryPage.getTotalElements(), breweryResponsePage.getTotalElements());
        assertEquals(breweryPage.getSize(), breweryResponsePage.getSize());
        assertEquals(breweryPage.getContent().size(), breweryResponsePage.getContent().size());
        assertEquals(breweryPage.getContent().get(0).getBreweryId(), breweryResponsePage.getContent().get(0).getId());

    }

    @Test
    @DisplayName("Should map breweryRequest to Brewery Entity keeping all values the same")
    void should_map_brewery_request_to_brewery_entity_keeping_all_values_the_same() {
        //when
        Brewery brewery = mapper.mapRequestEntityToEntity(breweryRequest);

        //then
        assertEquals(breweryRequest.getName(), brewery.getName());
        assertEquals(breweryRequest.getEmail(), brewery.getEmail());
        assertEquals(breweryRequest.getAddress().getStreet(), brewery.getAddress().getStreet());
        assertEquals(breweryRequest.getAddress().getLocalNumber(), brewery.getAddress().getLocalNumber());
        assertEquals(breweryRequest.getAddress().getZipCode(), brewery.getAddress().getZipCode());
        assertEquals(breweryRequest.getAddress().getParcelNumber(), brewery.getAddress().getParcelNumber());
        assertEquals(breweryRequest.getPhoneNumber(), brewery.getPhoneNumber());
        assertEquals(breweryRequest.getWebsite(), brewery.getWebsite());
    }

}
