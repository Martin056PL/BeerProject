package wawer.kamil.beerproject.dto.request;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class BreweryRequest {

    @NotEmpty(message = "Name for brewery is required")
    private String name;
    @Email(message = "Email has invalid format")
    @NotEmpty(message = "Email for brewery is required")
    private String email;
    private Long phoneNumber;
    private @Valid AddressRequest address;
    private String website;
    private List<@Valid BeerRequest> beerList;
}
