package wawer.kamil.beerproject.utils.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import wawer.kamil.beerproject.dto.request.BeerRequest;
import wawer.kamil.beerproject.dto.response.BeerResponse;
import wawer.kamil.beerproject.model.Beer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static wawer.kamil.beerproject.helpers.BeerTestHelper.*;

class BeerMapperTest {

    private final BeerMapper beerMapper = new BeerMapper(new ModelMapper());
    private Beer beer;
    private Page<Beer> beerPage;
    private List<Beer> beerList;
    private BeerRequest beerRequest;

    @BeforeEach
    void setUp() {
        this.beer = getBeer();
        this.beerPage = getBeerPage();
        this.beerList = getListOfBeers();
        this.beerRequest = getBeerRequest();
    }

    @Test
    @DisplayName("Should map beer to BeerResponse keeping all values the same")
    void should_map_beer_to_BeerResponse_keeping_all_values_the_same() {
        //when
        BeerResponse beerResponse = beerMapper.mapBeerToBeerResponse(beer);

        //then
        assertEquals(beer.getBeerId(), beerResponse.getId());
        assertEquals(beer.getName(), beerResponse.getName());
        assertEquals(beer.getAlcohol(), beerResponse.getAlcohol());
        assertEquals(beer.getExtract(), beerResponse.getExtract());
        assertEquals(beer.getStyle(), beerResponse.getStyle());
    }

    @Test
    @DisplayName("Should map beerPage to BeerResponsePage keeping all values the same")
    void should_map_beer_entity_page_to_beer_response_page_keeping_all_values_the_same() {
        //when
        Page<BeerResponse> beerResponsePage = beerMapper.mapBeerEntityPageToBeerResponsePage(beerPage);

        //then
        assertEquals(beerPage.getTotalPages(), beerResponsePage.getTotalPages());
        assertEquals(beerPage.getTotalElements(), beerResponsePage.getTotalElements());
        assertEquals(beerPage.getSize(), beerResponsePage.getSize());
        assertEquals(beerPage.getContent().size(), beerResponsePage.getContent().size());
        assertEquals(beerPage.getContent().get(0).getBeerId(), beerResponsePage.getContent().get(0).getId());
    }

    @Test
    @DisplayName("Should map list of beer to list of BeerResponse keeping all values the same")
    void should_map_list_of_beer_entity_to_list_of_beer_response_keeping_all_values_the_same() {
        //when
        List<BeerResponse> beerResponsePage = beerMapper.mapListOfBeerEntityToListBeerResponse(beerList);

        //then
        assertEquals(beerList.size(), beerResponsePage.size());
        assertEquals(beerList.get(0).getBeerId(), beerResponsePage.get(0).getId());
    }

    @Test
    @DisplayName("Should map beerRequest to Beer Entity keeping all values the same")
    void should_map_beer_request_to_Beer_entity_keeping_all_values_the_same() {
        //when
        Beer beer = beerMapper.mapBeerRequestToBeerEntity(beerRequest);

        //then
        assertEquals(beerRequest.getName(), beer.getName());
        assertEquals(beerRequest.getAlcohol(), beer.getAlcohol());
        assertEquals(beerRequest.getExtract(), beer.getExtract());
        assertEquals(beerRequest.getStyle(), beer.getStyle());
    }
}
