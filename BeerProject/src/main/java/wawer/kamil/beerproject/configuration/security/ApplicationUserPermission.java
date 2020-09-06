package wawer.kamil.beerproject.configuration.security;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ApplicationUserPermission {

    USER_READ("user:read"),
    USER_CREATE("user:create"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),
    EXHIBITOR_READ("exhibitor:read"),
    EXHIBITOR_CREATE("exhibitor:create"),
    EXHIBITOR_UPDATE("exhibitor:update"),
    EXHIBITOR_DELETE("exhibitor:delete"),
    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:create"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete");

    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }
}
