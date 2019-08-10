package wawer.kamil.beerproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name = "beer")
public class Beer implements Serializable {

    private static final long serialVersionUID = -6229128548566797958L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beer_id")
    private Long beerId;

    private String name;

    /*@Enumerated(EnumType.STRING)
    private StyleBeer style;*/

    private Integer extract;

    private Double alcohol;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "brewery_id")
    @JsonIgnore
    private Brewery brewery;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> ingredients;

}
