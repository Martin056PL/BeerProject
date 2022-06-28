package wawer.kamil.beerproject.helpers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.model.user.User;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static wawer.kamil.beerproject.helpers.UserRegistrationHelper.getValidUserRegistrationData;

public class UserTestHelper {

    public static Pageable createPageable(int page, int size) {
        return PageRequest.of(page, size);
    }

    public static Page<UserResponse> createUserResponsePage() {
        return new PageImpl<>(createListOfUsersResponse());
    }

    public static List<UserResponse> createListOfUsersResponse() {
        return Collections.singletonList(createUserResponse());
    }

    public static Page<User> createUserPage() {
        return new PageImpl<>(createListOfUser());
    }

    public static List<User> createListOfUser() {
        return List.of(getUserEntityWithUserRole());
    }

    public static User getUserEntityWithUserRole() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setEmail("user@email.com");
        user.setPassword("user");
        user.setGrantedAuthorities(crateGrantedAuthoritiesWithUserRole());
        user.setUserRegistrationData(getValidUserRegistrationData());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        return user;
    }

    public static User getUserEntityWithHashedPassword() {
        User user = new User();
        user.setId(0L);
        user.setUsername("user");
        user.setEmail("user@email.com");
        user.setPassword("hashed.password");
        user.setGrantedAuthorities(crateGrantedAuthoritiesWithUserRole());
        user.setUserRegistrationData(getValidUserRegistrationData());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(false);
        return user;
    }

    public static User getUserEntityWithHashedPasswordAndExhibitorRole() {
        User user = new User();
        user.setId(0L);
        user.setUsername("user");
        user.setEmail("user@email.com");
        user.setPassword("hashed.password");
        user.setGrantedAuthorities(crateGrantedAuthoritiesWithExhibitorRole());
        user.setUserRegistrationData(getValidUserRegistrationData());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(false);
        return user;
    }

    public static User getUserEntityWithHashedPasswordAndAdminRole() {
        User user = new User();
        user.setId(0L);
        user.setUsername("user");
        user.setEmail("user@email.com");
        user.setPassword("hashed.password");
        user.setGrantedAuthorities(crateGrantedAuthoritiesWithAdminRole());
        user.setUserRegistrationData(getValidUserRegistrationData());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(false);
        return user;
    }

    public static User getDisabledUserEntity() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setEmail("user@email.com");
        user.setPassword("user");
        user.setGrantedAuthorities(crateGrantedAuthoritiesWithUserRole());
        user.setUserRegistrationData(getValidUserRegistrationData());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(false);
        return user;
    }

    public static UserResponse createUserResponse() {
        UserResponse user = new UserResponse();
        user.setId(1L);
        user.setUsername("user");
        user.setEmail("user@email.com");
        user.setPassword("user");
        user.setGrantedAuthorities(crateGrantedAuthoritiesWithUserRole());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        return user;
    }

    public static Set<String> crateGrantedAuthoritiesWithUserRole() {
        return Stream.of("user:read", "ROLE_USER").collect(Collectors.toSet());
    }

    public static Set<String> crateGrantedAuthoritiesWithExhibitorRole() {
        return Stream.of(
                "ROLE_EXHIBITOR",
                "exhibitor:read",
                "exhibitor:create",
                "exhibitor:update",
                "exhibitor:delete"
        ).collect(Collectors.toSet());
    }

    public static Set<String> crateGrantedAuthoritiesWithAdminRole() {
        return Stream.of(
                "ROLE_ADMIN",
                "admin:read",
                "admin:create",
                "admin:update",
                "admin:delete"
        ).collect(Collectors.toSet());
    }

    public static UserRequest getUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("user");
        userRequest.setPassword("user");
        userRequest.setEmail("test@email.com");
        userRequest.setGrantedAuthorities(crateGrantedAuthoritiesWithUserRole());
        return userRequest;
    }


}
