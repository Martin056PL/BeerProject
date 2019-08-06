package wawer.kamil.beerproject.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@Entity
public class Beer implements Serializable {

    private static final long serialVersionUID = -6229128548566797958L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beer_id")
    private Long beerId;
    private String name;
    private String brewery;
    private StyleBeer style;
    private Integer extract;
    private Integer alcohol;
    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> ingredients;

}
