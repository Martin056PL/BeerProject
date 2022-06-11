package wawer.kamil.beerproject.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.dto.response.UserRegistrationResponse;
import wawer.kamil.beerproject.service.RegistrationUserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wawer.kamil.beerproject.helpers.UserRegistrationHelper.getUserRegistrationRequest;
import static wawer.kamil.beerproject.helpers.UserRegistrationHelper.getUserRegistrationResponse;

@ExtendWith(MockitoExtension.class)
class RegistrationControllerTest {

    @InjectMocks
    RegistrationController controller;

    @Mock
    RegistrationUserService service;

    private UserRegistrationRequest request;
    private UserRegistrationResponse response;

    private static final Long ID = 1L;
    private static final String TOKEN = "b8c20230-e993-11ec-8fea-0242ac120002";

    @BeforeEach
    void setup(){
        this.request = getUserRegistrationRequest();
        this.response = getUserRegistrationResponse();
    }

    @Test
    @DisplayName("Should return status CREATED with response body when controller returns registered user")
    void should_return_status_created_with_response_body_when_controller_returns_registered_user() {
        // given
        when(service.registerNewUser(request)).thenReturn(response);

        //when
        ResponseEntity<UserRegistrationResponse> responseEntity = controller.signUpNewUser(request);

        //then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
    }

    @Test
    @DisplayName("Should return status OK with response body when controller returns new generated token")
    void should_return_status_OK_with_response_body_when_controller_returns_new_generated_token() {
        // given
        when(service.refreshRegistrationToken(ID)).thenReturn(TOKEN);

        //when
        ResponseEntity<String> responseEntity = controller.refreshRegistrationToken(ID);

        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(TOKEN, responseEntity.getBody());
    }

    @Test
    @DisplayName("Should return status OK with response body when confirm new account")
    void should_return_status_OK_with_response_body_when_confirm_new_account() {
        //when
        ResponseEntity<Object> responseEntity = controller.confirmAccount(ID, TOKEN);

        //then
        verify(service).confirmCredentials(ID, TOKEN);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
