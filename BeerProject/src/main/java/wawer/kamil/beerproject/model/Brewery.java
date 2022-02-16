package wawer.kamil.beerproject.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "beerList")
@Table(name = "brewery")
public class Brewery implements Serializable {

    private static final long serialVersionUID = -9149691662724951820L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brewery_id")
    private Long breweryId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotNull
    @Column(name = "phone_number")
    private Long phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @NotEmpty
    private String website;

    @OneToMany(
            mappedBy = "brewery",
            cascade = CascadeType.ALL
    )
    private List<Beer> beerList;

    @Lob
    @Column(name = "brewery_image", columnDefinition = "mediumblob")
    private byte[] breweryImage;


    public void addBeer(Beer beer) {
        if (beerList == null) {
            beerList = new ArrayList<>();
        }
        beerList.add(beer);
        beer.setBrewery(this);

    }
}
