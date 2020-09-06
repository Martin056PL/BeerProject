package wawer.kamil.beerproject.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wawer.kamil.beerproject.model.User;
import wawer.kamil.beerproject.repositories.UserRepository;

import java.time.LocalDateTime;

import static wawer.kamil.beerproject.configuration.security.ApplicationUserRole.USER;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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



    /* @GetMapping
    public ResponseEntity<Page<User>> findAllUsersPage(Pageable pageable) {
        log.debug("Endpoint address: 'users' with GET method, request parameter - pageable: {}", pageable);
        Page<User> resultListUsers = service.findAllUsersPage(pageable);
        log.debug("List of returned Id: {}", resultListUsers.stream().map(User::getId).collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.OK).body(resultListUsers);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> findAllUsersList() {
        log.debug("Endpoint address: 'users/list' with GET method");
        List<User> resultList = service.findAllUsersList();
        log.debug("List of returned Id: {}", resultList.stream().map(User::getId).collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.OK).body(resultList);
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
