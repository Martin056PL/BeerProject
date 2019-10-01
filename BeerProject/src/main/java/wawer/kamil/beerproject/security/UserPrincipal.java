package wawer.kamil.beerproject.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import wawer.kamil.beerproject.model.User;
import wawer.kamil.beerproject.model.UserInfo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode
@Builder
public class UserPrincipal implements UserDetails {

    private Long id;
    private UserInfo userInfo;
    private String username;
    private Boolean locked;
    private Boolean activated;
    private Collection<? extends GrantedAuthority> authorities;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;


    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return UserPrincipal.builder().
                id(user.getId())
                .userInfo(user.getUserInfo())
                .username(user.getUsername())
                .locked(user.getLocked())
                .activated(user.getActivated())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
