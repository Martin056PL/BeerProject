package wawer.kamil.beerproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BEER")
public class Beer implements Serializable {

    private static final long serialVersionUID = -6229128548566797958L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beer_id", nullable = false)
    private Long beerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "style", nullable = false)
    private String style;

    @Column(name = "extract", nullable = false)
    private Double extract;

    @Column(name = "alcohol", nullable = false)
    private Double alcohol;

    @ManyToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            targetEntity = Brewery.class
    )
    @JoinColumn(name = "brewery_id", nullable = false)
    @JsonIgnore
    private Brewery brewery;

    @Lob
    @Column(name = "beer_image", columnDefinition = "mediumblob")
    private byte[] beerImage;
}