package wawer.kamil.beerproject.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wawer.kamil.beerproject.model.Address;

@Getter
@Setter
@NoArgsConstructor
public class BreweryRequest {

    private String name;
    private String email;
    private Long phoneNumber;
    private Address address;
    private String website;

}
