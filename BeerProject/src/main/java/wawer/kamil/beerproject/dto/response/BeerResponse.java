package wawer.kamil.beerproject.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class BeerResponse {

    private Long id;
    private String name;
    private String style;
    private Double extract;
    private Double alcohol;
}
