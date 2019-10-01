package wawer.kamil.beerproject.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import wawer.kamil.beerproject.domain.enums.RoleName;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60, name = "role_name")
    private RoleName name;

    public Role(RoleName name) {
        this.name = name;
    }
}
