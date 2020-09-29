package wawer.kamil.beerproject.dto;

import lombok.Builder;
import lombok.Getter;
import wawer.kamil.beerproject.model.Address;

@Builder
@Getter
public class BreweryDTO {

    private long breweryId;
    private String name;
    private String email;
    private Long phoneNumber;
    private Address address;
    private String website;
}
