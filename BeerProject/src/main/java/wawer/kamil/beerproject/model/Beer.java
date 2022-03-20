package wawer.kamil.beerproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.io.Serializable;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "beer")
public class Beer implements Serializable {

    private static final long serialVersionUID = -6229128548566797958L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beer_id")
    private Long beerId;

    @Column(name = "name")
    private String name;

    @Column(name = "style")
    private String style;

    @Column(name = "extract")
    private Double extract;

    @Column(name = "alcohol")
    private Double alcohol;

    @ManyToOne(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY,
            targetEntity = Brewery.class
    )
    @JoinColumn(name = "brewery_id")
    @JsonIgnore
    private Brewery brewery;

    @Lob
    @Column(name = "beer_image", columnDefinition = "mediumblob")
    private byte[] beerImage;
}