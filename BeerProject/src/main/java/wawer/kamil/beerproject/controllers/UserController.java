package wawer.kamil.beerproject.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.exceptions.UsernameAlreadyExistsException;
import wawer.kamil.beerproject.generators.Generator;
import wawer.kamil.beerproject.model.User;
import wawer.kamil.beerproject.repositories.UserRepository;
import wawer.kamil.beerproject.service.UserService;

import java.util.List;

@RestController
@RequestMapping("users")
@Slf4j(topic = "application.logger")
public class UserController {

    private final UserRepository userRepository;
    private final Generator generator;
    private final UserService userService;

    public UserController(UserRepository userRepository, Generator generator, UserService userService) {
        this.userRepository = userRepository;
        this.generator = generator;
        this.userService = userService;
    }

    @GetMapping("/generate")
    public ResponseEntity<User> generateUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(generator.createUser()));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> findAllUsersPage(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAllUsersPage(pageable));
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponse>> findAllUsersList() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAllUsersList());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findUserByUserId(@PathVariable Long userId) throws NoContentException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createNewUser(@RequestBody UserRequest userRequest) throws UsernameAlreadyExistsException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addNewUser(userRequest));
    }
/*
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) throws NoContentException {
        log.debug("Endpoint address: 'user/{userId}' with PUT method, request parameter - userId: {};  user data: {}; {}; {}; {}"
                , userId
                , userDTO.getFirstName()
                , userDTO.getLastName()
                , userDTO.getEmail()
                , userDTO.getPhoneNumber());
        User result = service.updateUser(userId, mapper.map(userDTO, User.class));
        log.debug("Updated brewery with Id: {}", result.getId());
        return ResponseEntity.status(HttpStatus.OK).body(mapper.map(result, UserDTO.class));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId) throws NoContentException {
        log.debug("Endpoint address: 'user/{userId}' with DELETE method, request parameter - id: {}", userId);
        service.deleteUser(userId);
        log.debug("Deleted user with Id: {}", userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }*/
}
