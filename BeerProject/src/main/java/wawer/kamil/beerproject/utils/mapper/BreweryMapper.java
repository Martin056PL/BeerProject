package wawer.kamil.beerproject.utils.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.dto.BreweryDTO;
import wawer.kamil.beerproject.dto.request.BreweryRequest;
import wawer.kamil.beerproject.dto.response.BreweryResponse;
import wawer.kamil.beerproject.model.Beer;
import wawer.kamil.beerproject.model.Brewery;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static wawer.kamil.beerproject.service.impl.BreweryServiceHelper.getBeerListForBrewery;

@Component
public class BreweryMapper {

    private final ModelMapper modelMapper;
    private final ModelMapper modelMapperForBreweryUpdate;

    public BreweryMapper(@Qualifier(value = "defaultMapper") ModelMapper modelMapper,
                         @Qualifier(value = "BreweryMapper") ModelMapper modelMapperForBreweryUpdate) {
        this.modelMapper = modelMapper;
        this.modelMapperForBreweryUpdate = modelMapperForBreweryUpdate;
    }

    public List<BreweryResponse> mapBreweryEntityPageToBreweryResponsePage(List<Brewery> breweryPage) {
        return breweryPage.stream().map(brewery -> modelMapper.map(brewery, BreweryResponse.class)).collect(Collectors.toList());
    }

    public Page<BreweryResponse> mapBreweryEntityPageToBreweryResponsePage123(Page<Brewery> breweryPage) {
        return breweryPage.map(brewery -> modelMapper.map(brewery, BreweryResponse.class));//.collect(Collectors.toList());
    }

    public Brewery mapBreweryRequestToBreweryEntity(BreweryRequest breweryRequest) {
        return modelMapper.map(breweryRequest, Brewery.class);
    }

    public static List<BreweryDTO> mapBreweryEntitiesToBreweryDTOList(List<Brewery> breweryList) {
        return breweryList.stream()
                .map(brewery -> BreweryDTO.builder()
                        .breweryId(brewery.getBreweryId())
                        .name(brewery.getName())
                        .email(brewery.getEmail())
                        .phoneNumber(brewery.getPhoneNumber())
                        .website(brewery.getWebsite())
                        .address(brewery.getAddress())
                        .build()).collect(Collectors.toList());
    }

    public static Function<BreweryDTO, Brewery> mapBreweryDTOToBreweryEntity(List<Beer> beersByBreweryId) {
        return bp -> Brewery.builder()
                .breweryId(bp.getBreweryId())
                .name(bp.getName())
                .email(bp.getEmail())
                .phoneNumber(bp.getPhoneNumber())
                .website(bp.getWebsite())
                .address(bp.getAddress())
                .beerList(getBeerListForBrewery(beersByBreweryId, bp))
                .build();
    }


}
