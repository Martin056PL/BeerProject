package wawer.kamil.beerproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@Table(name = "brewery")
public class Brewery implements Serializable {

    private static final long serialVersionUID = -9149691662724951820L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brewery_id")
    private Long breweryId;

    private String name;

    private String email;

    @Column(name = "phone_number")
    private Long phoneNumber;

    private String address;

    private String website;

    @ElementCollection(fetch = FetchType.LAZY)
    @JsonIgnore
    @OneToMany(mappedBy = "brewery",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Beer> beerList;


    public void addBeer(Beer beer) {
        if (beerList == null) {
            beerList = new ArrayList<>();
        }
        beerList.add(beer);
        beer.setBrewery(this);

    }
}
