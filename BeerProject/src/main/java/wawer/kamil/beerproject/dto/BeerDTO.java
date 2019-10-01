package wawer.kamil.beerproject.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import wawer.kamil.beerproject.model.Brewery;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class BeerDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String style;

    @Min(0)
    private Double extract;

    @Min(0)
    private Double alcohol;

    @ToString.Exclude
    @JsonIgnore
    private Brewery brewery;

    @JsonIgnore
    private byte [] beerImage;
}
