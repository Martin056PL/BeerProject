package wawer.kamil.beerproject.dto.request;

import lombok.*;
import wawer.kamil.beerproject.model.Address;
import wawer.kamil.beerproject.model.Beer;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
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
}
