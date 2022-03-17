package wawer.kamil.beerproject.dto.request;

import lombok.*;
import wawer.kamil.beerproject.model.Address;
import wawer.kamil.beerproject.model.Beer;


import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class BreweryRequest {


    private String name;

    private String email;

    private Long phoneNumber;

    private Address address;

    private String website;

    private List<Beer> beerList;
}
