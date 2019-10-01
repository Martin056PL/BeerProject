package wawer.kamil.beerproject.model;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import wawer.kamil.beerproject.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
public class User extends DateAudit implements Serializable {

    private static final long serialVersionUID = -2398213833013356134L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "user",
            cascade = PERSIST)
    private UserInfo userInfo;

    @NotEmpty
    @Column(name = "username",
            unique = true,
            nullable = false)
    private String username;

    @NaturalId
    @NotEmpty
    @Email
    @Column(name = "email",
            unique = true,
            nullable = false)
    private String email;

    @NotEmpty
    @Column(name = "password",
            nullable = false)
    private String password;

    @Column(name = "activated",
            nullable = false)
    private Boolean activated;

    @Column(name = "locked",
            nullable = false)
    private Boolean locked;

    @ManyToMany(fetch = LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(@NotEmpty String username, @NotEmpty @Email String email, @NotEmpty String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.activated = false;
        this.locked = false;
    }
}
