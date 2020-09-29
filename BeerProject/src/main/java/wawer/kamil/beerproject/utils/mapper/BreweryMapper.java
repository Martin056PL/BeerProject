package wawer.kamil.beerproject.utils.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.dto.request.BreweryRequest;
import wawer.kamil.beerproject.model.Brewery;

@Component
public class BreweryMapper {

    private final ModelMapper modelMapper;
    private final ModelMapper modelMapperForBreweryUpdate;

    public BreweryMapper(@Qualifier(value = "defaultMapper") ModelMapper modelMapper,
                         @Qualifier(value = "BreweryMapper") ModelMapper modelMapperForBreweryUpdate) {
        this.modelMapper = modelMapper;
        this.modelMapperForBreweryUpdate = modelMapperForBreweryUpdate;
    }

    public Brewery mapBreweryRequestToBreweryEntity(BreweryRequest breweryRequest) {
        return modelMapper.map(breweryRequest, Brewery.class);
    }


}
