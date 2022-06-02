package wawer.kamil.beerproject.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import wawer.kamil.beerproject.model.helpers.UserDetailsHelper;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "USER")
public class User extends DataAudit implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(
            name = "USER_GRANTED_AUTHORITIES",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "granted_authorities")
    private Set<String> grantedAuthorities;

    @Column(name = "is_account_non_expired")
    private boolean isAccountNonExpired;

    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked;

    @Column(name = "is_credentials_non_expired")
    private boolean isCredentialsNonExpired;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private UserRegistrationData userRegistrationData;

    public User(
            String username,
            String password,
            String email,
            UserDetailsHelper userDetailsHelper
    ) {
        super();
        this.username = username;
        this.password = password;
        this.email = email;
        this.grantedAuthorities = userDetailsHelper.getGrantedAuthorities();
        this.isAccountNonExpired = userDetailsHelper.isAccountNonExpired();
        this.isAccountNonLocked = userDetailsHelper.isAccountNonLocked();
        this.isCredentialsNonExpired = userDetailsHelper.isCredentialsNonExpired();
        this.isEnabled = userDetailsHelper.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public User withRegistrationData(UserRegistrationData userRegistrationData) {
        this.userRegistrationData = userRegistrationData;
        userRegistrationData.setUser(this);
        return this;
    }
}
