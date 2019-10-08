package wawer.kamil.beerproject.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import wawer.kamil.beerproject.model.User;
import wawer.kamil.beerproject.dto.UserDTO;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.service.UserService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    UserService service;

    @Mock
    Pageable pageable;

    @Mock
    Page<User> page;

    @Mock
    List<User> userList;

    @Mock
    ModelMapper mapper;

    @Mock
    User user;

    @Mock
    UserDTO userDTO;

    @InjectMocks
    UserController controller;

    private final static Long ID = 1L;

    @Test
    public void should_return_response_entity_which_equals_to_controller_response_entity_user_page() {
        when(service.findAllUsersPage(pageable)).thenReturn(page);
        assertEquals(ResponseEntity.ok().body(page), controller.findAllUsersPage(pageable));
    }

    @Test
    public void should_return_status_code_which_equals_to_controller_status_code_user_page() throws NoContentException {
        when(service.findAllUsersPage(pageable)).thenReturn(page);
        assertEquals(HttpStatus.OK, controller.findAllUsersPage(pageable).getStatusCode());
    }

    @Test
    public void should_return_response_entity_which_equals_to_controller_response_entity_user_list() {
        when(service.findAllUsersList()).thenReturn(userList);
        assertEquals(ResponseEntity.ok().body(userList), controller.findAllUsersList());
    }

    @Test
    public void should_return_status_code_which_equals_to_controller_status_code_user_list() throws NoContentException {
        when(service.findAllUsersList()).thenReturn(userList);
        assertEquals(HttpStatus.OK, controller.findAllUsersList().getStatusCode());
    }

    @Test
    public void should_return_response_entity_which_equals_to_controller_response_entity_base_on_user_id() throws NoContentException {
        when(mapper.map(service.findUserByUserId(ID), UserDTO.class)).thenReturn(userDTO);
        assertEquals(ResponseEntity.ok().body(userDTO), controller.findUserByUserId(ID));
    }

    @Test
    public void should_return_status_code_which_equals_to_controller_status_code_base_on_user_id() throws NoContentException {
        when(mapper.map(service.findUserByUserId(ID), UserDTO.class)).thenReturn(userDTO);
        assertEquals(HttpStatus.OK, controller.findUserByUserId(ID).getStatusCode());
    }

    @Test
    public void should_return_saved_user_which_equals_to_user_saved_by_controller(){
        when(mapper.map(userDTO, User.class)).thenReturn(user);
        when(service.createNewUser(user)).thenReturn(user);
        when(mapper.map(user, UserDTO.class)).thenReturn(userDTO);
        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(userDTO),controller.createNewUser(userDTO));
    }

    @Test
    public void should_return_status_code_which_equals_to_controller_status_code_base_on_saved_user(){
        when(mapper.map(userDTO, User.class)).thenReturn(user);
        when(service.createNewUser(user)).thenReturn(user);
        when(mapper.map(user, UserDTO.class)).thenReturn(userDTO);
        assertEquals(HttpStatus.CREATED,controller.createNewUser(userDTO).getStatusCode());
    }

    @Test
    public void should_return_saved_user_which_equals_to_user_updated_by_controller() throws NoContentException {
        when(mapper.map(userDTO, User.class)).thenReturn(user);
        when(service.updateUser(ID, user)).thenReturn(user);
        when(mapper.map(user, UserDTO.class)).thenReturn(userDTO);
        when(user.getId()).thenReturn(ID);
        assertEquals(ResponseEntity.status(HttpStatus.OK).body(userDTO),controller.updateUser(ID, userDTO));
    }

    @Test
    public void should_return_status_code_which_equals_to_controller_status_code_base_on_update_user() throws NoContentException {
        when(mapper.map(userDTO, User.class)).thenReturn(user);
        when(service.updateUser(ID, user)).thenReturn(user);
        when(mapper.map(user, UserDTO.class)).thenReturn(userDTO);
        assertEquals(HttpStatus.OK,controller.updateUser(ID, userDTO).getStatusCode());
    }

    @Test
    public void should_return_status_code_which_equals_to_controller_status_code_base_on_deleted_user() throws NoContentException {
        assertEquals(HttpStatus.NO_CONTENT, controller.deleteUser(ID).getStatusCode());
    }
}
