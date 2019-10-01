package wawer.kamil.beerproject.domain;

import lombok.*;
import org.hibernate.annotations.NaturalId;
import wawer.kamil.beerproject.domain.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
public class User extends DateAudit {

    private static final long serialVersionUID = -2398213833013356134L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty
    @Column(name = "username")
    private String username;

    @NaturalId
    @NotEmpty
    @Column(name = "email", unique = true)
    @Email
    private String email;

    @NotEmpty
    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @ManyToMany(fetch = LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(@NotEmpty String firstName, @NotEmpty String lastName, @NotEmpty String username, @NotEmpty @Email String email, @NotEmpty String password, Long phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
