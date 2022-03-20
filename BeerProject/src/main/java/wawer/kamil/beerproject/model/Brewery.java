package wawer.kamil.beerproject.model;

import lombok.*;

import javax.persistence.*;

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


    @Column(nullable = false)
    private String name;


    private String email;


    @Column(name = "phone_number",nullable = false)
    private Long phoneNumber;

    @OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;


    private String website;

    @OneToMany(
            mappedBy = "brewery",
            targetEntity = Beer.class,
            cascade = CascadeType.ALL
    )
    private List<Beer> beerList;

    @Lob
    @Column(name = "brewery_image", columnDefinition = "mediumblob")
    private byte[] breweryImage;


    public Beer addBeer(Beer beer) {
        if (beerList == null) {
            beerList = new ArrayList<>();
        }
        beerList.add(beer);
        beer.setBrewery(this);
        return beer;
    }
}
