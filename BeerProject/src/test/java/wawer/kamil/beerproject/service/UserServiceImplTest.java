package wawer.kamil.beerproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.exceptions.ElementNotFoundException;
import wawer.kamil.beerproject.exceptions.UsernameAlreadyExistsException;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.model.user.factory.UserFactory;
import wawer.kamil.beerproject.repositories.UserRepository;
import wawer.kamil.beerproject.service.impl.UserServiceImpl;
import wawer.kamil.beerproject.utils.mapper.UserMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wawer.kamil.beerproject.configuration.security.ApplicationUserRole.USER;
import static wawer.kamil.beerproject.helpers.UserRegistrationHelper.getUserRegistrationRequest;
import static wawer.kamil.beerproject.helpers.UserTestHelper.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository repository;

    @Mock
    UserMapper mapper;

    @Mock
    UserFactory factory;

    @Mock
    Pageable pageable;

    @InjectMocks
    UserServiceImpl service;

    private User user;
    private User disabledUser;
    private UserRequest userRequest;
    private UserResponse userResponse;
    private UserRegistrationRequest userRegistrationRequest;
    private final static Long ID = 1L;
    private final static String USERNAME = "user";

    @BeforeEach
    void setUp() {
        this.user = getUserEntityWithUserRole();
        this.userRequest = getUserRequest();
        this.userResponse = createUserResponse();
        this.userRegistrationRequest = getUserRegistrationRequest();
        this.disabledUser = getDisabledUserEntity();
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
    void verify_findById_while_calling_getUserByUserId() {
        //given
        when(repository.findById(ID)).thenReturn(Optional.ofNullable(user));
        when(mapper.mapUserEntityToUserResponse(user)).thenReturn(userResponse);
        //when
        service.getUserById(ID);

        //then
        verify(repository).findById(ID);
    }

    @Test
    @DisplayName("verify save when calling saveUser")
    void verify_save_when_calling_saveUser(){
        //when
        service.saveUser(user);

        //then
        verify(repository).save(user);
    }

    @Test
    @DisplayName("should return user with registration data base on registration request")
    void should_return_user_with_registration_data_base_on_registration_request(){
        //given
        when(repository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        when(factory.createNewUser(userRegistrationRequest, USER)).thenReturn(user);

        //when
        User userWithUserRegistrationData = service.getUserWithUserRegistrationData(userRegistrationRequest, USER);

        //then
        assertEquals("user", userWithUserRegistrationData.getUsername());
    }

    @Test
    @DisplayName("Should throw usernameAlreadyExistException when username already exist")
    void should_throw_username_already_exist_exception_when_username_already_exist() {
        //then
        assertThrows(UsernameAlreadyExistsException.class, this::callGetUserWithUserRegistrationDataWithUsernameWhichAlreadyExist);
    }

    private void callGetUserWithUserRegistrationDataWithUsernameWhichAlreadyExist() throws UsernameAlreadyExistsException {
        //given
        when(repository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

        //when
        service.getUserWithUserRegistrationData(userRegistrationRequest, USER);
    }

    @Test
    @DisplayName("verify findById when calling findUserById")
    void verify_findById_when_calling_findUserById(){
        //given
        when(repository.findById(ID)).thenReturn(Optional.of(user));

        //when
        service.findUserById(ID);

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
        service.saveUser(userRequest);

        //then
        verify(repository).existsUserByUsername(user.getUsername());
        verify(repository).save(user);
    }

    @Test
    @DisplayName("Verify if existsUserByUsername and save during creating new user")
    void should_update_and_update_user_account() {
        //given
        assertFalse(disabledUser.isEnabled());
        assertFalse(disabledUser.getUserRegistrationData().isConfirmed());

        //when
        service.enableUserAccount(disabledUser);

        //then
        assertTrue(disabledUser.isEnabled());
        assertTrue(disabledUser.getUserRegistrationData().isConfirmed());
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
        service.saveUser(userRequest);
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
