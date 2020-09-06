package wawer.kamil.beerproject.configuration.security;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static wawer.kamil.beerproject.configuration.security.ApplicationUserPermission.*;

@Getter
@ToString
public enum ApplicationUserRole {

    USER(Set.of(USER_READ)),
    EXHIBITOR(Set.of(EXHIBITOR_READ, EXHIBITOR_UPDATE, EXHIBITOR_CREATE, EXHIBITOR_DELETE)),
    ADMIN(Set.of(ADMIN_READ, ADMIN_CREATE, ADMIN_UPDATE, ADMIN_DELETE));

    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }


}
