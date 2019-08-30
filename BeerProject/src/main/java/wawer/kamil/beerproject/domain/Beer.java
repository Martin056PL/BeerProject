package wawer.kamil.beerproject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "beer")
public class Beer implements Serializable {

    private static final long serialVersionUID = -6229128548566797958L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beer_id")
    private Long beerId;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @Column(name = "style")
    private String style;

    @Min(value = 0)
    @Column(name = "extract")
    private Double extract;

    @Size(min = 0, max = 100)
    @Column(name = "alcohol")
    private Double alcohol;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "brewery_id")
    @JsonIgnore
    private Brewery brewery;

    @Lob
    @Column(name = "beer_image", columnDefinition = "mediumblob")
    private byte[] beerImage;
}
