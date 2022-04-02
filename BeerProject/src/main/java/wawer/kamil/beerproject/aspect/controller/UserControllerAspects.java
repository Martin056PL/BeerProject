package wawer.kamil.beerproject.aspect.controller;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.dto.request.UserRequest;

@Aspect
@Component
@Slf4j(topic = "application.logger")
public class UserControllerAspects {

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.UserController.findAllUsersPage(..)) && args(pageable)")
    public void logFindAllUsersPage(Pageable pageable) {
        log.debug("Endpoint address: 'users' with GET method, request parameter - pageable: {}", pageable);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.UserController.findAllUsersList(..))")
    public void logFindAllUsersList() {
        log.debug("Endpoint address: 'users/list' with GET method");
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.UserController.findUserByUserId(..)) && args(id)")
    public void logFindUserById(Long id) {
        log.debug("Endpoint address: 'users/{userId}' with GET method, request parameter - id: {}", id);
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.UserController.createNewUser(..)) && args(userRequest)")
    public void logSaveNewUser(UserRequest userRequest) {
        log.debug("Endpoint address: 'user' with POST method, request parameter - user data: {}; {}; {}"
                , userRequest.getUsername()
                , userRequest.getEmail()
                , userRequest.getGrantedAuthorities());
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.UserController.updateUser(..)) && args(userId, userRequest)", argNames = "userId, userRequest")
    public void logUpdateUser(Long userId, UserRequest userRequest) {
        log.debug("Endpoint address: 'user/{userId}' with PUT method, request parameter - userId: {};  user data: {}; {}; {}"
                , userId
                , userRequest.getUsername()
                , userRequest.getEmail()
                , userRequest.getGrantedAuthorities());
    }

    @Before(value = "execution(* wawer.kamil.beerproject.controllers.UserController.deleteUserPermanently(..)) && args(id)")
    public void logDeleteUserById(long id) {
        log.debug("Endpoint address: 'user/{id}' with DELETE method, request parameter - id: {}", id);
    }
}
