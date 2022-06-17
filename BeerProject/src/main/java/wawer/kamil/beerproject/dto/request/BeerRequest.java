package wawer.kamil.beerproject.dto.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BeerRequest {

    @NotEmpty(message = "Name for beer is required")
    private String name;
    @NotEmpty(message = "Style for beer is required")
    private String style;
    @Min(value = 0, message = "Extract must be grater then 0")
    private Double extract;
    @Min(value = 0, message = "Alcohol must be grater then 0")
    private Double alcohol;
    private BreweryRequest brewery;
}
