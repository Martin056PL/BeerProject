package wawer.kamil.beerproject.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wawer.kamil.beerproject.model.Address;
import wawer.kamil.beerproject.model.Beer;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BreweryRequest {

    @NotEmpty
    private String name;
    @NotEmpty
    private String email;
    @NotEmpty
    private Long phoneNumber;
    @NotEmpty
    private Address address;
    @NotEmpty
    private String website;
    private List<Beer> beerList;
    private byte[] breweryImage;
}
