package wawer.kamil.beerproject.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Builder
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

}
