package wawer.kamil.beerproject.dto.request;

import lombok.*;
import wawer.kamil.beerproject.model.Brewery;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class BeerRequest {

    @NotEmpty
    private String name;
    private String style;
    private Double extract;

    @Min(0)
    @Max(100)
    private Double alcohol;
    private Brewery brewery;
}
