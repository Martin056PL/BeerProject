package wawer.kamil.beerproject.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.model.User;
import wawer.kamil.beerproject.service.UserService;

import java.util.List;

@RestController
@RequestMapping("users")
@Slf4j(topic = "application.logger")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/generate")
    public ResponseEntity<User> generateUser() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.generateDefaultUserToDatabase());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> findAllUsersPage(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAllUsersPage(pageable));
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> findAllUsersList() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAllUsersList());
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> findUserByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserByUserId(userId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> createNewUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addNewUser(userRequest));
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userId, userRequest));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUserPermanently(@PathVariable Long userId) {
        userService.permanentDeleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
