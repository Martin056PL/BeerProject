package wawer.kamil.beerproject.aspect.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.data.domain.Pageable;
import wawer.kamil.beerproject.controllers.UserController;
import wawer.kamil.beerproject.dto.request.UserRequest;
import wawer.kamil.beerproject.dto.response.UserResponse;
import wawer.kamil.beerproject.service.UserService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static wawer.kamil.beerproject.helpers.UserTestHelper.getUserRequest;
import static wawer.kamil.beerproject.helpers.UserTestHelper.getUserResponse;

@ExtendWith(MockitoExtension.class)
class UserControllerAspectsTest {

    @Mock
    UserService service;

    @Mock
    Pageable pageable;

    UserController userControllerProxy;
    PrintStream old = System.out;
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    UserRequest userRequest;
    UserResponse userResponse;

    private static final Long ID = 1L;

    @BeforeEach
    void setUp() {
        this.userControllerProxy = configureBreweryControllerProxy();
        this.userRequest = getUserRequest();
        this.userResponse = getUserResponse();
        setCustomSetOut();
    }

    @AfterEach
    void cleanAfter() {
        setDefaultSetOut();
    }

    @Test
    @DisplayName("Should throw log when findAllUsersPage is called with pageable param")
    void should_throw_log_when_findAllUsersPage_is_called_with_id_with_param() {
        //when
        userControllerProxy.findAllUsersPage(pageable);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                String.format("[main] DEBUG application.logger - Endpoint address: 'users' with GET method, request parameter - pageable: %s", pageable.toString()),
                removeDateTimeFromLog(testLog)
        );
    }

    @Test
    @DisplayName("Should throw log when findAllUsersList is called with pageable param")
    void should_throw_log_when_findAllUsersList_is_called_with_id_with_param() {
        //when
        userControllerProxy.findAllUsersList();
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                "[main] DEBUG application.logger - Endpoint address: 'users/list' with GET method",
                removeDateTimeFromLog(testLog)
        );
    }

    @Test
    @DisplayName("Should throw log when findUserByUserId is called with pageable param")
    void should_throw_log_when_findUserByUserId_is_called_with_id_with_param() {
        //when
        userControllerProxy.findUserByUserId(ID);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                String.format("[main] DEBUG application.logger - Endpoint address: 'users/{userId}' with GET method, request parameter - id: %s", ID),
                removeDateTimeFromLog(testLog)
        );
    }

    @Test
    @DisplayName("Should throw log when createNewUser is called with pageable param")
    void should_throw_log_when_createNewUser_is_called_with_id_with_param() {
        //when
        userControllerProxy.createNewUser(userRequest);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                String.format(
                        "[main] DEBUG application.logger - Endpoint address: 'user' with POST method, request parameter - user data: %s; %s; %s",
                        userRequest.getUsername(),
                        userRequest.getEmail(),
                        userRequest.getGrantedAuthorities()
                        ),
                removeDateTimeFromLog(testLog)
        );
    }

    @Test
    @DisplayName("Should throw log when updateUser is called with pageable param")
    void should_throw_log_when_updateUser_is_called_with_id_with_param() {
        //when
        userControllerProxy.updateUser(ID, userRequest);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                String.format(
                        "[main] DEBUG application.logger - Endpoint address: 'user/{userId}' with PUT method, request parameter - userId: %s;  user data: %s; %s; %s",
                        ID,
                        userRequest.getUsername(),
                        userRequest.getEmail(),
                        userRequest.getGrantedAuthorities()
                ),
                removeDateTimeFromLog(testLog)
        );
    }

    @Test
    @DisplayName("Should throw log when deleteUserPermanently is called with pageable param")
    void should_throw_log_when_deleteUserPermanently_is_called_with_id_with_param() {
        //when
        userControllerProxy.deleteUserPermanently(ID);
        String testLog = byteArrayOutputStream.toString();

        //then
        assertEquals(
                String.format("[main] DEBUG application.logger - Endpoint address: 'user/{id}' with DELETE method, request parameter - id: %s", ID),
                removeDateTimeFromLog(testLog)
        );
    }


    private UserController configureBreweryControllerProxy() {
        UserController userController = new UserController(service);
        AspectJProxyFactory factory = new AspectJProxyFactory(userController);
        factory.setTarget(userController);
        factory.addAspect(UserControllerAspects.class);
        return factory.getProxy();
    }

    private void setCustomSetOut() {
        System.setOut(new PrintStream(byteArrayOutputStream));
    }

    private void setDefaultSetOut() {
        System.out.flush();
        System.setOut(old);
    }

    private String removeDateTimeFromLog(String originalLog) {
        int indexOfLogBeginning = 13;
        return originalLog.substring(indexOfLogBeginning).trim();
    }
}
