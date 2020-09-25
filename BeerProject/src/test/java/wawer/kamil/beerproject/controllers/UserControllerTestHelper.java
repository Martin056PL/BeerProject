package wawer.kamil.beerproject.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class UserControllerTestHelper {

    static Pageable createPageable(int page, int size) {
        return PageRequest.of(page, size);
    }

    static Page<UserResponse> createPage() {
        return new PageImpl<>(createListOfUsers());
    }

    static List<UserResponse> createListOfUsers() {
        return Collections.singletonList(createUserResponse());
    }

    static UserResponse createUserResponse() {
        UserResponse user = new UserResponse();
        user.setId(1L);
        user.setUsername("user");
        user.setEmail("user@email.com");
        user.setPassword("user");
        user.setGrantedAuthorities(crateGrantedAuthorities());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        return user;
    }

    static Set<String> crateGrantedAuthorities() {
        return Stream.of("USER_ROLE", "user:read").collect(Collectors.toSet());
    }

    static UserRequest createUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("user");
        userRequest.setPassword("user");
        userRequest.setEmail("test@email.com");
        userRequest.setGrantedAuthorities(crateGrantedAuthorities());
        return userRequest;
    }


}
