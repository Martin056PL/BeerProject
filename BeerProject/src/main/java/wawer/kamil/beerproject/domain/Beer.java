package wawer.kamil.beerproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

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

    private String style;

    private Double extract;

    private Double alcohol;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "brewery_id")
    @JsonIgnore
    private Brewery brewery;

    @Lob
    @Column(name = "image", columnDefinition = "mediumblob")
    private byte [] image;
}
