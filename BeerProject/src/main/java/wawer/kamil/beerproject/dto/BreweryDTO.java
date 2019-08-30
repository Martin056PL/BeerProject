package wawer.kamil.beerproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import wawer.kamil.beerproject.domain.Address;
import wawer.kamil.beerproject.domain.Beer;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class BreweryDTO {

    private String name;

    private String email;

    private Long phoneNumber;

    private Address address;

    private String website;

    @JsonIgnore
    private List<Beer> beerList;

}
