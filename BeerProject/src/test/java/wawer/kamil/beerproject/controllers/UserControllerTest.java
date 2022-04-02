package wawer.kamil.beerproject.controllers;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.UsernameAlreadyExistsException;
import wawer.kamil.beerproject.model.User;
import wawer.kamil.beerproject.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static wawer.kamil.beerproject.helpers.UserTestHelper.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    UserController controller;

    private UserResponse userResponse;
    private Page<UserResponse> userResponsePage;
    private List<UserResponse> userResponseList;
    private Pageable pageable;
    private UserRequest userRequest;

    private final static Long USER_ID = 1L;
    private final static int PAGE_SIZE = 1;
    private final static int PAGE_PAGE = 1;

    @BeforeEach
    void setUp() {
        this.userResponse = getUserResponse();
        this.userResponsePage = getUserResponsePage();
        this.userResponseList = getUsersListResponse();
        this.pageable = getPageable(PAGE_PAGE, PAGE_SIZE);
        this.userRequest = getUserRequest();
    }

    @Test
    @DisplayName("Test - should return response entity with generated user")
    void should_return_response_entity_with_generated_user() {
        //when
        ResponseEntity<User> userResponseEntity = controller.generateUser();

        //than
        assertThat(userResponseEntity).isNotNull();
        assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userResponseEntity.getBody()).isNull();
    }

    @Test
    @DisplayName("Test - should return response entity with page body")
    void should_return_response_entity_with_page_body() {
        //given
        when(userService.findAllUsersPage(pageable)).thenReturn(userResponsePage);

        //when
        ResponseEntity<Page<UserResponse>> allUsersPageResponseEntity = controller.findAllUsersPage(pageable);

        //than
        assertThat(allUsersPageResponseEntity).isNotNull();
        assertThat(allUsersPageResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(allUsersPageResponseEntity.getBody()).isEqualTo(userResponsePage);
        assertThat(allUsersPageResponseEntity.getBody().getTotalElements()).isEqualTo(1L);
        assertThat(allUsersPageResponseEntity.getBody().getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName("Test - should return response entity with list body")
    void should_return_response_entity_with_list_body() {
        //given
        when(userService.findAllUsersList()).thenReturn(userResponseList);

        //when
        ResponseEntity<List<UserResponse>> allUsersListResponseEntity = controller.findAllUsersList();

        //than
        assertThat(allUsersListResponseEntity).isNotNull();
        assertThat(allUsersListResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(allUsersListResponseEntity.getBody()).isNotNull();
        assertThat(allUsersListResponseEntity.getBody().size()).isEqualTo(1);
    }


    @Test
    @DisplayName("Test - should return response entity with proper user base on id body")
    void should_return_response_entity_with_proper_user_base_on_id_body() {
        //given
        when(userService.findUserByUserId(USER_ID)).thenReturn(userResponse);

        //when
        ResponseEntity<UserResponse> userByUserIdResponseEntity = controller.findUserByUserId(USER_ID);

        //than
        assertThat(userByUserIdResponseEntity).isNotNull();
        assertThat(userByUserIdResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userByUserIdResponseEntity.getBody()).isEqualTo(userResponse);
        assertThat(userByUserIdResponseEntity.getBody()).isNotNull();
        assertThat(userByUserIdResponseEntity.getBody().getId()).isEqualTo(1L);
        assertThat(userByUserIdResponseEntity.getBody().getUsername()).isEqualTo("user");
        assertThat(userByUserIdResponseEntity.getBody().getPassword()).isEqualTo("user");
        assertThat(userByUserIdResponseEntity.getBody().getEmail()).isEqualTo("user@email.com");
        assertThat(userByUserIdResponseEntity.getBody().getGrantedAuthorities().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test - should throw exception when user not found")
    void should_throw_exception_when_user_not_found() {
        //given
        when(userService.findUserByUserId(USER_ID)).thenThrow(ElementNotFoundException.class);

        //when
        assertThatThrownBy(() -> controller.findUserByUserId(USER_ID));
    }

    @Test
    @DisplayName("Test - should return response entity with saved user")
    void should_return_response_entity_with_saved_user() {
        //given
        when(userService.addNewUser(userRequest)).thenAnswer(invocation -> {
            UserRequest argument = invocation.getArgument(0, UserRequest.class);
            UserResponse userResponse = new UserResponse();
            userResponse.setId(2L);
            userResponse.setUsername(argument.getUsername());
            userResponse.setPassword(argument.getPassword());
            userResponse.setEmail(argument.getEmail());
            userResponse.setGrantedAuthorities(argument.getGrantedAuthorities());
            userResponse.setEnabled(true);
            userResponse.setCredentialsNonExpired(true);
            userResponse.setAccountNonLocked(true);
            userResponse.setAccountNonExpired(true);
            return userResponse;
        });

        //when
        ResponseEntity<UserResponse> newUserResponseEntity = controller.createNewUser(userRequest);

        //than
        assertThat(newUserResponseEntity).isNotNull();
        assertThat(newUserResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(newUserResponseEntity.getBody()).isNotNull();
        assertThat(newUserResponseEntity.getBody().getId()).isEqualTo(2L);
        assertThat(newUserResponseEntity.getBody().getUsername()).isEqualTo("user");
        assertThat(newUserResponseEntity.getBody().getPassword()).isEqualTo("user");
        assertThat(newUserResponseEntity.getBody().getEmail()).isEqualTo("test@email.com");
        assertThat(newUserResponseEntity.getBody().isAccountNonExpired()).isTrue();
        assertThat(newUserResponseEntity.getBody().isAccountNonLocked()).isTrue();
        assertThat(newUserResponseEntity.getBody().isCredentialsNonExpired()).isTrue();
        assertThat(newUserResponseEntity.getBody().isEnabled()).isTrue();
    }

    @Test
    @DisplayName("Test - should throw exception when username is unavailable during create")
    void should_throw_exception_when_username_is_unavailable_during_create() {
        //given
        when(userService.addNewUser(userRequest)).thenThrow(UsernameAlreadyExistsException.class);

        //when
        assertThatThrownBy(() -> controller.createNewUser(userRequest));
    }

    @Test
    @DisplayName("Test - should return response entity with updated user")
    void should_return_response_entity_with_updated_user() {
        //given
        when(userService.updateUser(USER_ID, userRequest)).thenAnswer(invocation -> {
            UserRequest argument = invocation.getArgument(1, UserRequest.class);
            UserResponse userResponse = new UserResponse();
            userResponse.setId(USER_ID);
            userResponse.setUsername(argument.getUsername());
            userResponse.setPassword(argument.getPassword());
            userResponse.setEmail(argument.getEmail());
            userResponse.setGrantedAuthorities(argument.getGrantedAuthorities());
            userResponse.setEnabled(true);
            userResponse.setCredentialsNonExpired(true);
            userResponse.setAccountNonLocked(true);
            userResponse.setAccountNonExpired(true);
            return userResponse;
        });

        //when
        ResponseEntity<UserResponse> newUserResponseEntity = controller.updateUser(USER_ID, userRequest);

        //than
        assertThat(newUserResponseEntity).isNotNull();
        assertThat(newUserResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(newUserResponseEntity.getBody()).isNotNull();
        assertThat(newUserResponseEntity.getBody().getId()).isEqualTo(1L);
        assertThat(newUserResponseEntity.getBody().getUsername()).isEqualTo("user");
        assertThat(newUserResponseEntity.getBody().getPassword()).isEqualTo("user");
        assertThat(newUserResponseEntity.getBody().getEmail()).isEqualTo("test@email.com");
        assertThat(newUserResponseEntity.getBody().isAccountNonExpired()).isTrue();
        assertThat(newUserResponseEntity.getBody().isAccountNonLocked()).isTrue();
        assertThat(newUserResponseEntity.getBody().isCredentialsNonExpired()).isTrue();
        assertThat(newUserResponseEntity.getBody().isEnabled()).isTrue();
    }

    @Test
    @DisplayName("Test - should throw exception when user not found during update")
    void should_throw_exception_when_user_not_found_during_update() {
        //given
        when(userService.updateUser(USER_ID, userRequest)).thenThrow(ElementNotFoundException.class);

        //when
        assertThatThrownBy(() -> controller.updateUser(USER_ID, userRequest));
    }

    @Test
    @DisplayName("Test - should return response entity when deleting user permanently")
    void should_return_response_entity_when_deleting_user_permanently() {
        //when
        ResponseEntity<?> responseEntity = controller.deleteUserPermanently(USER_ID);

        //than
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(responseEntity.getBody()).isNull();
    }

    @Test
    @DisplayName("Test - should throw exception when user not found during deleting user permanently")
    void should_throw_exception_when_user_not_found_during_deleting_user_permanently() {
        //given
        when(controller.deleteUserPermanently(USER_ID)).thenThrow(ElementNotFoundException.class);

        //when
        assertThatThrownBy(() -> controller.deleteUserPermanently(USER_ID));
    }
}