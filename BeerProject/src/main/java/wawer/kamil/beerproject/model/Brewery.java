package wawer.kamil.beerproject.model;

import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import wawer.kamil.beerproject.model.audited.JpaAuditedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BREWERY")
@Audited
public class Brewery extends JpaAuditedEntity implements Serializable {

    private static final long serialVersionUID = -9149691662724951820L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    private Long version;

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Column(nullable = false)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private Long phoneNumber;

    @OneToOne(targetEntity = Address.class, mappedBy = "brewery")
    @NotNull
    @NotAudited
    private Address address;

    private String website;

    @OneToMany(
            mappedBy = "brewery",
            targetEntity = Beer.class,
            cascade = CascadeType.ALL
    )
    @NotAudited
    @ToString.Exclude
    private List<Beer> beerList;

    @Lob
    @Column(name = "brewery_image", columnDefinition = "mediumblob")
    private byte[] breweryImage;

    public void assignBreweryToAllBeersOnBreweriesList() {
        Optional.ofNullable(this.getBeerList())
                .ifPresent(beers -> beers.forEach(beer -> beer.setBrewery(this)));
    }

    public void assignAddressToBrewery(){
        Optional.ofNullable(this.address)
                .ifPresent(add -> add.setBrewery(this));
    }
}
