package wawer.kamil.beerproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "beer")
@ToString
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

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "alcohol")
    private Double alcohol;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "brewery_id")
    @JsonIgnore
    private Brewery brewery;

    @Lob
    @Column(name = "beer_image", columnDefinition = "mediumblob")
    private byte[] beerImage;
}