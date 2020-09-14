package wawer.kamil.beerproject.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.exceptions.NoContentException;
import wawer.kamil.beerproject.model.User;
import wawer.kamil.beerproject.repositories.UserRepository;
import wawer.kamil.beerproject.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static wawer.kamil.beerproject.configuration.security.ApplicationUserRole.USER;

@RestController
@RequestMapping("users")
@Slf4j(topic = "application.logger")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/generate")
    public void generateUser() {
        User user = new User(
                LocalDateTime.now(),
                "user",
                passwordEncoder.encode("user"),
                "kamil.wawer@pollub.edu.pl",
                USER.getGrantedAuthority(),
                true,
                true,
                true,
                true
        );
        userRepository.save(user);
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
/*
    @PostMapping
    public ResponseEntity<UserDTO> createNewUser(@RequestBody UserDTO userDTO) {
        log.debug("Endpoint address: 'user' with POST method, request parameter - user data: {}; {}; {}; {}"
                , userDTO.getFirstName()
                , userDTO.getLastName()
                , userDTO.getEmail()
                , userDTO.getPhoneNumber());
        User result = service.createNewUser(mapper.map(userDTO, User.class));
        log.debug("Add new user with Id: {}", result.getId());
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
