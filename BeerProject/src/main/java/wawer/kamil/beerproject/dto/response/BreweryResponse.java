package wawer.kamil.beerproject.dto.response;

import lombok.*;

import java.util.List;

@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
public class BreweryResponse {

    private Long id;
    private String name;
    private String email;
    private Long phoneNumber;
    private AddressResponse address;
    private String website;
    private List<BeerResponse> beerList;
}
