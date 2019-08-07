package wawer.kamil.beerproject.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Beer implements Serializable {

    private static final long serialVersionUID = -6229128548566797958L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beer_id")
    private Long beerId;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brewery_id")
    private Brewery brewery;

    private StyleBeer style;

    private Integer extract;

    private Double alcohol;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> ingredients;

}
