package wawer.kamil.beerproject.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)

public class AddressRequest {

    @NotEmpty(message = "Street for address is required")
    private String street;
    @NotEmpty(message = "Parcel number for address is required")
    private String parcelNumber;
    private String localNumber;
    private String zipCode;
    @NotEmpty(message = "City for address is required")
    private String city;
}
