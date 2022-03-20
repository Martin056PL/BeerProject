package wawer.kamil.beerproject.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class AddressResponse {

    private Long addressId;
    private String street;
    private String parcelNumber;
    private String localNumber;
    private String zipCode;
    private String city;
}
