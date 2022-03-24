package wawer.kamil.beerproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.UsernameAlreadyExistsException;
import wawer.kamil.beerproject.model.User;
import wawer.kamil.beerproject.repositories.UserRepository;
import wawer.kamil.beerproject.service.impl.UserServiceImpl;
import wawer.kamil.beerproject.utils.mapper.UserMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wawer.kamil.beerproject.helpers.UserTestHelper.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository repository;

    @Mock
    UserMapper mapper;

    @Mock
    Pageable pageable;

    @InjectMocks
    UserServiceImpl service;

    private User user;
    private UserRequest userRequest;
    private UserResponse userResponse;

    private final static Long ID = 1L;
    private final static String USERNAME = "SomePreDefinedUsername";

    @BeforeEach
    void setUp() {
        this.user = getUserEntity();
        this.userRequest = getUserRequest();
        this.userResponse = createUserResponse();
    }

    @Test
    @DisplayName("Verify if findAllByUsername is called during getting UserDetails")
    void verify_if_findAllByUsername_is_called_during_getting_UserDetails() {
        //given
        when(repository.findByUsername(USERNAME)).thenReturn(Optional.ofNullable(user));

        //when
        service.loadUserByUsername(USERNAME);

        //then
        verify(repository).findByUsername(USERNAME);
    }

    @Test
    @DisplayName("Verify if findAll is called during getting Page<UserResponse>")
    void verify_get_all_users_page() {
        //when
        service.findAllUsersPage(pageable);

        //then
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("Verify if findAll is called during getting List<UserResponse>")
    void verify_get_all_users_list() {
        //when
        service.findAllUsersList();

        //then
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Verify if findById is called during finding User by ID")
    void verify_findById_while_calling_findUserByUserId() {
        //given
        when(repository.findById(ID)).thenReturn(Optional.ofNullable(user));
        when(mapper.mapUserEntityToUserResponse(user)).thenReturn(userResponse);
        //when
        service.findUserByUserId(ID);

        //then
        verify(repository).findById(ID);
    }

    @Test
    @DisplayName("Verify if existsUserByUsername and save during creating new user")
    void verify_existsUserByUsername_and_save_new_user() {
        //given
        when(repository.existsUserByUsername(userRequest.getUsername())).thenReturn(false);
        when(mapper.mapUserRequestToUserEntity(userRequest)).thenReturn(user);

        //when
        service.addNewUser(userRequest);

        //then
        verify(repository).existsUserByUsername(user.getUsername());
        verify(repository).save(user);
    }


    @Test
    @DisplayName("Verify if UsernameAlreadyExistsException is thrown when username already exists during save new user")
    void verify_addNewUser_when_username_already_exists() {
        //then
        assertThrows(UsernameAlreadyExistsException.class, this::callAddNewUserWithUsernameWhichAlreadyExist);
    }

    private void callAddNewUserWithUsernameWhichAlreadyExist() throws UsernameAlreadyExistsException {
        //given
        when(repository.existsUserByUsername(userRequest.getUsername())).thenReturn(true);

        //when
        service.addNewUser(userRequest);
    }

    @Test
    @DisplayName("Verify if findById is called during updating user")
    void verify_findById_is_called_when_user_id_exists() {
        //given
        when(repository.findById(ID)).thenReturn(Optional.ofNullable(user));
        when(mapper.mapUserRequestToUserEntity(userRequest, user)).thenReturn(user);
        when(mapper.mapUserEntityToUserResponse(user)).thenReturn(userResponse);

        //when
        service.updateUser(ID, userRequest);

        //then
        verify(repository).findById(ID);
    }

    @Test
    @DisplayName("Verify if ElementNotFoundException is thrown when user id does not exists during update")
    void verify_permanentDeleteUser_when_user_id_is_not_exists3() {
        //then
        assertThrows(ElementNotFoundException.class, this::callFindByIdWhichDoesNotExistDuringUpdate);
    }

    private void callFindByIdWhichDoesNotExistDuringUpdate() {
        //given
        when(repository.findById(ID)).thenReturn(Optional.empty());

        //when
        service.updateUser(ID, userRequest);
    }

    @Test
    @DisplayName("Verify if findById is called during deleting user")
    void verify_delete_user_by_user_id_when_user_id_exists() {
        //given
        when(repository.findById(ID)).thenReturn(Optional.ofNullable(user));

        //when
        service.permanentDeleteUser(ID);

        //then
        verify(repository).delete(user);
    }

    @Test
    @DisplayName("Verify if ElementNotFoundException is thrown when user id does not exists during delete")
    void verify_permanentDeleteUser_when_user_id_is_not_exists() {
        //then
        assertThrows(ElementNotFoundException.class, this::callPermanentDeleteUserByIdWhichDoesNotExist);
    }

    private void callPermanentDeleteUserByIdWhichDoesNotExist() {
        //given
        when(repository.findById(ID)).thenReturn(Optional.empty());

        //when
        service.permanentDeleteUser(ID);
    }
}
