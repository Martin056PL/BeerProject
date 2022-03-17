package wawer.kamil.beerproject.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 5341237096226780217L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;


    @Column(name = "street")
    private String street;

    @Column(name = "parcel_number")
    private Integer parcelNumber;


    @Column(name = "local_number")
    private Integer localNumber;


    @Column(name = "zip_code")
    private String zipCode;


    @Column(name = "city")
    private String city;
}
