package wawer.kamil.beerproject.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 5341237096226780217L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @NotEmpty
    @Column(name = "street")
    private String street;

    @NotEmpty
    @Column(name = "parcel_number")
    private Integer parcelNumber;

    @Column(name = "local_number")
    private Integer localNumber;

    @NotEmpty
    @Column(name = "zip_code")
    private String zipCode;

    @NotEmpty
    @Column(name = "city")
    private String city;
}
