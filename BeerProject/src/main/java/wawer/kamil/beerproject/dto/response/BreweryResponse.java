package wawer.kamil.beerproject.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wawer.kamil.beerproject.model.Address;
import wawer.kamil.beerproject.model.Beer;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class BreweryResponse {

    private Long id;
    private String name;
    private String email;
    private Long phoneNumber;
    private Address address;
    private String website;
    private List<Beer> beerList;
    private byte[] breweryImage;
}
