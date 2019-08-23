package wawer.kamil.beerproject.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wawer.kamil.beerproject.domain.User;
import wawer.kamil.beerproject.dto.UserDTO;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("users")
@Slf4j(topic = "application.logger")
public class UserController {

    private final UserService service;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<Page<User>> findAllUsersPage(Pageable pageable) {
        log.debug("Endpoint address: 'users' with GET method, request parameter - pageable: {}", pageable);
        Page<User> resultListUsers = service.findAllUsersPage(pageable);
        log.debug("List of returned Id: {}", resultListUsers.stream().map(User::getUserId).collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.OK).body(resultListUsers);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> findAllUsersList() {
        log.debug("Endpoint address: 'users/list' with GET method");
        List<User> resultList = service.findAllUsersList();
        log.debug("List of returned Id: {}", resultList.stream().map(User::getUserId).collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.OK).body(resultList.stream()
                .map(user -> mapper.map(user, UserDTO.class)).collect(Collectors.toList()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> findUserByUserId(@PathVariable Long userId) throws NoContentException {
        log.debug("Endpoint address: 'users/{userId}' with GET method, request parameter - id: {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.map(service.findUserByUserId(userId), UserDTO.class));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createNewUser(@RequestBody UserDTO userDTO) {
        log.debug("Endpoint address: 'user' with POST method, request parameter - user data: {}; {}; {}; {}"
                , userDTO.getFirstName()
                , userDTO.getLastName()
                , userDTO.getEmail()
                , userDTO.getPhoneNumber());
        User result = service.createNewUser(mapper.map(userDTO, User.class));
        log.debug("Add new user with Id: {}", result.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(service.createNewUser(result), UserDTO.class));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) throws NoContentException {
        log.debug("Endpoint address: 'user/{userId}' with PUT method, request parameter - userId: {};  user data: {}; {}; {}; {}"
                , userId
                , userDTO.getFirstName()
                , userDTO.getLastName()
                , userDTO.getEmail()
                , userDTO.getPhoneNumber());
        User result = service.updateUser(userId, mapper.map(userDTO, User.class));
        log.debug("Updated brewery with Id: {}", result.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(mapper.map(result, UserDTO.class));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId) throws NoContentException {
        log.debug("Endpoint address: 'user/{userId}' with DELETE method, request parameter - id: {}", userId);
        service.deleteUser(userId);
        log.debug("Deleted user with Id: {}", userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
