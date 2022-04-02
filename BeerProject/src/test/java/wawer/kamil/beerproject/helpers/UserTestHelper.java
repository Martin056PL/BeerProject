package wawer.kamil.beerproject.helpers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.model.User;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserTestHelper {

    public static Pageable getPageable(int page, int size) {
        return PageRequest.of(page, size);
    }

    public static Page<UserResponse> getUserResponsePage() {
        return new PageImpl<>(getUsersListResponse());
    }

    public static List<UserResponse> getUsersListResponse() {
        return Collections.singletonList(getUserResponse());
    }

    public static Page<User> getUsersPage() {
        return new PageImpl<>(getUsers());
    }

    public static List<User> getUsers(){
        return List.of(getUserEntity());
    }

    public static User getUserEntity(){
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setEmail("user@email.com");
        user.setPassword("user");
        user.setGrantedAuthorities(getGrantedAuthorities());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        return user;
    }

    public static UserResponse getUserResponse() {
        UserResponse user = new UserResponse();
        user.setId(1L);
        user.setUsername("user");
        user.setEmail("user@email.com");
        user.setPassword("user");
        user.setGrantedAuthorities(getGrantedAuthorities());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        return user;
    }

    public static Set<String> getGrantedAuthorities() {
        return Stream.of("USER_ROLE", "user:read").collect(Collectors.toSet());
    }

    public static UserRequest getUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("user");
        userRequest.setPassword("user");
        userRequest.setEmail("test@email.com");
        userRequest.setGrantedAuthorities(getGrantedAuthorities());
        return userRequest;
    }


}
