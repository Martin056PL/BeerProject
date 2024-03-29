package wawer.kamil.beerproject.aspect.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.dto.response.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j(topic = "application.logger")
public class UserServiceAspects {

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.UserServiceImpl.findAllUsersPage(..))", returning = "resultPage")
    public void logAllFoundUsersAsPage(Page<UserResponse> resultPage) {
        log.debug("List of returned Id: {}", resultPage.stream().map(UserResponse::getId).collect(Collectors.toList()));
    }

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.UserServiceImpl.findAllUsersList(..))", returning = "resultList")
    public void logAllFoundUsersAsList(List<UserResponse> resultList) {
        log.debug("List of returned Id: {}", resultList.stream().map(UserResponse::getId).collect(Collectors.toList()));
    }

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.UserServiceImpl.getUserById(..))", returning = "user")
    public void logFoundUserById(UserResponse user) {
        log.debug("Found user with id: {}", user.getId());
    }

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.UserServiceImpl.saveUser(..))", returning = "user")
    public void logAddNewUser(UserResponse user) {
        log.debug("Add new user with Id: {}", user.getId());
    }

    @AfterReturning(value = "execution(* wawer.kamil.beerproject.service.impl.UserServiceImpl.updateUser(..))", returning = "user")
    public void logUpdateUser(UserResponse user) {
        log.debug("Updated brewery with Id: {}", user.getId());
    }

    @After(value = "execution(* wawer.kamil.beerproject.service.impl.UserServiceImpl.permanentDeleteUser(..)) && args(userId)")
    public void logDeletedUser(long userId) {
        log.debug("Deleted user with Id: {}", userId);
    }

}