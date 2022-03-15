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
import static wawer.kamil.beerproject.helpers.UserTestHelper.getUserRequest;
import static wawer.kamil.beerproject.helpers.UserTestHelper.getUserEntity;

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

    private final static Long ID = 1L;
    private final static String USERNAME = "SomePreDefinedUsername";

    @BeforeEach
    void setUp() {
        this.user = getUserEntity();
        this.userRequest = getUserRequest();
    }

    @Test
    @DisplayName("Verify if findAllByUsername is called during getting UserDetails")
    void verify_if_findAllByUsername_is_called_during_getting_UserDetails(){
        service.loadUserByUsername(USERNAME);
        verify(repository).findAllByUsername(USERNAME);
    }

    @Test
    @DisplayName("Verify if findAll is called during getting Page<UserResponse>")
    void verify_get_all_users_page(){
        service.findAllUsersPage(pageable);
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("Verify if findAll is called during getting List<UserResponse>")
    void verify_get_all_users_list(){
        service.findAllUsersList();
        verify(repository).findAll();
    }

    @Test
    @DisplayName("Verify if findById is called during finding User by ID")
    void verify_findById_while_calling_findUserByUserId () throws ElementNotFoundException {
        when(repository.findById(ID)).thenReturn(Optional.ofNullable(user));
        service.findUserByUserId(ID);
        verify(repository).findById(ID);
    }

    @Test
    @DisplayName("Verify if existsUserByUsername and save during creating new user")
    void verify_existsUserByUsername_and_save_new_user() throws UsernameAlreadyExistsException {
        when(repository.existsUserByUsername(userRequest.getUsername())).thenReturn(false);
        when(mapper.mapUserRequestToUserEntity(userRequest)).thenReturn(user);
        service.addNewUser(userRequest);
        verify(repository).existsUserByUsername(user.getUsername());
        verify(repository).save(user);
    }


    @Test
    @DisplayName("Verify if UsernameAlreadyExistsException is thrown when username already exists during save new user")
    void verify_addNewUser_when_username_already_exists() {
        assertThrows(UsernameAlreadyExistsException.class, this::callAddNewUserWithUsernameWhichAlreadyExist);
    }

    private void callAddNewUserWithUsernameWhichAlreadyExist() throws UsernameAlreadyExistsException {
        when(repository.existsUserByUsername(userRequest.getUsername())).thenReturn(true);
        service.addNewUser(userRequest);
    }

    @Test
    @DisplayName("Verify if findById is called during updating user")
    void verify_findById_is_called_when_user_id_exists() throws ElementNotFoundException {
        when(repository.findById(ID)).thenReturn(Optional.ofNullable(user));
        service.updateUser(ID, userRequest);
        verify(repository).findById(ID);
    }

    @Test
    @DisplayName("Verify if ElementNotFoundException is thrown when user id does not exists during update")
    void verify_permanentDeleteUser_when_user_id_is_not_exists3() {
        assertThrows(ElementNotFoundException.class, this::callFindByIdWhichDoesNotExistDuringUpdate);
    }

    private void callFindByIdWhichDoesNotExistDuringUpdate() throws ElementNotFoundException {
        when(repository.findById(ID)).thenReturn(Optional.empty());
        service.updateUser(ID, userRequest);
    }

    @Test
    @DisplayName("Verify if findById is called during deleting user")
    void verify_delete_user_by_user_id_when_user_id_exists() throws ElementNotFoundException {
        when(repository.findById(ID)).thenReturn(Optional.ofNullable(user));
        service.permanentDeleteUser(ID);
        verify(repository).delete(user);
    }

    @Test
    @DisplayName("Verify if ElementNotFoundException is thrown when user id does not exists during delete")
    void verify_permanentDeleteUser_when_user_id_is_not_exists() {
        assertThrows(ElementNotFoundException.class, this::callPermanentDeleteUserByIdWhichDoesNotExist);
    }

    private void callPermanentDeleteUserByIdWhichDoesNotExist() throws ElementNotFoundException {
        when(repository.findById(ID)).thenReturn(Optional.empty());
        service.permanentDeleteUser(ID);
    }
}
