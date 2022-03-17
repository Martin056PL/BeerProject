package wawer.kamil.beerproject.dto.request;

import lombok.*;
import wawer.kamil.beerproject.model.Brewery;



@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class BeerRequest {


    private String name;
    private String style;
    private Double extract;


    private Double alcohol;
    private Brewery brewery;
}
