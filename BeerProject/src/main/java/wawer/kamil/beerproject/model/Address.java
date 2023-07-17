package wawer.kamil.beerproject.model;

import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.proxy.HibernateProxy;
import wawer.kamil.beerproject.model.audited.JpaAuditedEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ADDRESS")
@Audited
@Getter
@Setter
public class Address extends JpaAuditedEntity implements Serializable {

    private static final long serialVersionUID = 5341237096226780217L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Version
    private Long version;

    @OneToOne(targetEntity = Brewery.class)
    @JoinColumn(name = "brewery_id", nullable = false)
    @NotAudited
    @ToString.Exclude
    private Brewery brewery;

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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Address address = (Address) o;
        return getId() != null && Objects.equals(getId(), address.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", version=" + version +
                ", street='" + street + '\'' +
                ", parcelNumber='" + parcelNumber + '\'' +
                ", localNumber='" + localNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
