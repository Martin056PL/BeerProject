package wawer.kamil.beerproject.configuration.security;

import lombok.Getter;
import lombok.ToString;

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

    public Set<String> getGrantedAuthority() {
        Set<String> permissionsSet = getPermissions().stream()
                .map(ApplicationUserPermission::getPermission)
                .collect(Collectors.toSet());
        permissionsSet.add("ROLE_" + this.name());
        return permissionsSet;
    }
}
