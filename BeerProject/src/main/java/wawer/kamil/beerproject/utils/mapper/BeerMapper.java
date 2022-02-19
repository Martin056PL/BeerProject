package wawer.kamil.beerproject.utils.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.dto.request.BeerRequest;
import wawer.kamil.beerproject.dto.response.BeerResponse;
import wawer.kamil.beerproject.model.Beer;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BeerMapper {

    private final ModelMapper modelMapper;

    public BeerMapper(@Qualifier(value = "BeerMapper") ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Page<BeerResponse> mapBeerEntityPageToBeerResponsePage(Page<Beer> beerPage) {
        return beerPage.map(beer -> modelMapper.map(beer, BeerResponse.class));
    }

    public List<BeerResponse> mapListOfBeerEntityToListBeerResponse(List<Beer> beerPage) {
        return beerPage.stream().map(beer -> modelMapper.map(beer, BeerResponse.class)).collect(Collectors.toList());
    }

    public BeerResponse mapBeerToBeerResponse(Beer beer) {
        return modelMapper.map(beer, BeerResponse.class);
    }

    public Beer mapBeerRequestToBeerEntity(BeerRequest beerRequest) {
        return modelMapper.map(beerRequest, Beer.class);
    }
}
