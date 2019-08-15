package wawer.kamil.beerproject.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String street;

    @Column(name = "parcel_number")
    private Integer parcelNumber;

    @Column(name = "local_number")
    private Integer localNumber;

    @Column(name = "zip_code")
    private String zipCode;

    private String city;
}
