package wawer.kamil.beerproject.utils.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.dto.request.BreweryRequest;
import wawer.kamil.beerproject.dto.response.BreweryResponse;
import wawer.kamil.beerproject.model.Brewery;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BreweryMapper {

    private final ModelMapper modelMapper;

    public BreweryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<BreweryResponse> mapListOfBreweryEntityToListBreweryResponse(List<Brewery> breweryPage) {
        return breweryPage.stream().map(brewery -> modelMapper.map(brewery, BreweryResponse.class)).collect(Collectors.toList());
    }

    public Page<BreweryResponse> mapBreweryEntityPageToBreweryResponsePage(Page<Brewery> breweryPage) {
        return breweryPage.map(brewery -> modelMapper.map(brewery, BreweryResponse.class));
    }

    public BreweryResponse mapBreweryToBreweryResponse(Brewery brewery) {
        return modelMapper.map(brewery, BreweryResponse.class);
    }

    public Brewery mapBreweryRequestToBreweryEntity(BreweryRequest breweryRequest) {
        return modelMapper.map(breweryRequest, Brewery.class);
    }
}
