package wawer.kamil.beerproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ADDRESS")
public class Address implements Serializable {

    private static final long serialVersionUID = 5341237096226780217L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "parcel_number", nullable = false)
    private String parcelNumber;

    @Column(name = "local_number")
    private String localNumber;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city", nullable = false)
    private String city;
}
