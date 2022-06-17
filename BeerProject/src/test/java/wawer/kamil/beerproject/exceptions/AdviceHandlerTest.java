package wawer.kamil.beerproject.exceptions;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.MethodParameter;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static wawer.kamil.beerproject.helpers.AdviceHandlerHelper.*;

@SpringBootTest(properties = {"spring.profiles.active=test"})
class AdviceHandlerTest {

    @Autowired
    AdviceHandler adviceHandler;

    @Autowired
    ExceptionFormat exceptionFormat;

    @MockBean
    Exception exception;

    @MockBean
    PropertyReferenceException propertyReferenceException;

    @MockBean
    BindingResult bindingResult;

    @MockBean
    MethodParameter parameter;

    @MockBean
    HttpServletRequest request;

    @MockBean
    WebRequest webRequest;

    private RollbackException rollbackException;
    private MissingServletRequestParameterException missingServletRequestParameterException;
    private List<FieldError> fieldErrors;

    private static final String ERROR_MESSAGE_KEY = AdviceHandler.ERROR_MESSAGE_KEY;

    @BeforeEach
    void setUp() {
        this.rollbackException = getRollbackException();
        this.missingServletRequestParameterException = getMissingServletRequestParameterException();
        this.fieldErrors = getFieldErrors();
    }

    @Test
    @DisplayName("Should error message be the same as name of variable")
    void should_error_message_be_the_same_as_name_of_variable() {
        assertEquals("error_message", ERROR_MESSAGE_KEY);
    }

    @Test
    @DisplayName("Should return response entity with exception format relevant for not found")
    void should_return_response_entity_with_exception_format_relevant_for_handle_method_argument_not_valid() {
        //given
        String errorMessage = "{\"name\":\"Name for brewery is required\",\"email\":\"Email for brewery is required\"}";
        MethodArgumentNotValidException methodArgumentNotValidException = new MethodArgumentNotValidException(parameter, bindingResult);
        when(methodArgumentNotValidException.getBindingResult().getFieldErrors()).thenReturn(fieldErrors);

        //when
        ResponseEntity<Object> objectResponseEntity = adviceHandler.handleMethodArgumentNotValid(
                methodArgumentNotValidException,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                webRequest
        );
        ExceptionFormat body = (ExceptionFormat) objectResponseEntity.getBody();

        //then
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
        assertEquals(objectResponseEntity.getStatusCode(), body.getStatus());
        assertEquals(2, body.getErrorMessage().size());
        assertEquals(errorMessage, new JSONObject(body.getErrorMessage()).toString());
    }

    @Test
    @DisplayName("Should return response entity with exception format relevant for not found")
    void should_return_response_entity_with_exception_format_relevant_for_not_found() {
        //when
        ResponseEntity<Object> objectResponseEntity = adviceHandler.handleElementNotFoundException();
        ExceptionFormat body = (ExceptionFormat) objectResponseEntity.getBody();

        //then
        assertEquals(HttpStatus.NOT_FOUND, objectResponseEntity.getStatusCode());
        assertEquals(objectResponseEntity.getStatusCode(), body.getStatus());
        assertTrue(body.getErrorMessage().containsKey(ERROR_MESSAGE_KEY));
        assertEquals("Your item hasn't been found! Check request params!", body.getErrorMessage().get(ERROR_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Should return response entity with exception format relevant for bad image parameter")
    void should_return_response_entity_with_exception_format_relevant_for_bad_image_parameter() {
        //when
        ResponseEntity<Object> objectResponseEntity = adviceHandler.handleBadImageParameters();
        ExceptionFormat body = (ExceptionFormat) objectResponseEntity.getBody();

        //then
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
        assertEquals(objectResponseEntity.getStatusCode(), body.getStatus());
        assertTrue(body.getErrorMessage().containsKey(ERROR_MESSAGE_KEY));
        assertFalse(body.getErrorMessage().get(ERROR_MESSAGE_KEY).isEmpty());
        assertEquals("Your image has unhanded file type or it is over 10MB. Check again your image!", body.getErrorMessage().get(ERROR_MESSAGE_KEY));

    }

    @Test
    @DisplayName("Should return response entity with exception format relevant for rollback")
    void should_return_response_entity_with_exception_format_relevant_for_rollback() {
        //when
        ResponseEntity<Object> objectResponseEntity = adviceHandler.handleRollbackException(rollbackException);
        ExceptionFormat body = (ExceptionFormat) objectResponseEntity.getBody();

        //then
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
        assertEquals(objectResponseEntity.getStatusCode(), body.getStatus());
        assertTrue(body.getErrorMessage().containsKey(ERROR_MESSAGE_KEY));
        assertFalse(body.getErrorMessage().get(ERROR_MESSAGE_KEY).isEmpty());
        assertEquals(rollbackException.getLocalizedMessage(), body.getErrorMessage().get(ERROR_MESSAGE_KEY));
    }

    @Test
    @DisplayName("Should return response entity with exception format relevant for user already exist exception")
    void should_return_response_entity_with_exception_format_relevant_for_username_already_exists_exception() {
        //when
        ResponseEntity<Object> objectResponseEntity = adviceHandler.handleUsernameAlreadyExistsException();
        ExceptionFormat body = (ExceptionFormat) objectResponseEntity.getBody();

        //then
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
        assertEquals(objectResponseEntity.getStatusCode(), body.getStatus());
        assertTrue(body.getErrorMessage().containsKey(ERROR_MESSAGE_KEY));
        assertFalse(body.getErrorMessage().get(ERROR_MESSAGE_KEY).isEmpty());
        assertEquals("Username is unavailable", body.getErrorMessage().get(ERROR_MESSAGE_KEY));

    }

    @Test
    @DisplayName("Should return response entity with exception format relevant for MissingServletRequestParameterException")
    void should_return_response_entity_with_exception_format_relevant_for_missing_servlet_request_parameter_exception() {
        //when
        ResponseEntity<Object> objectResponseEntity = adviceHandler.handleMissingServletRequestParameter(missingServletRequestParameterException, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
        ExceptionFormat body = (ExceptionFormat) objectResponseEntity.getBody();

        //then
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
        assertEquals(objectResponseEntity.getStatusCode(), body.getStatus());
        assertTrue(body.getErrorMessage().containsKey(ERROR_MESSAGE_KEY));
        assertFalse(body.getErrorMessage().get(ERROR_MESSAGE_KEY).isEmpty());
        assertEquals(String.format("Request has not required parameter: %s", missingServletRequestParameterException.getMessage()), body.getErrorMessage().get(ERROR_MESSAGE_KEY));

    }

    @Test
    @DisplayName("Should return response entity with exception format relevant for PropertyReferenceException")
    void should_return_response_entity_with_exception_format_relevant_for_property_reference_exception() {
        //given
        String message = "message";
        when(propertyReferenceException.getMessage()).thenReturn(message);

        //when
        ResponseEntity<Object> objectResponseEntity = adviceHandler.handleInvalidRequestParameterException(propertyReferenceException);
        ExceptionFormat body = (ExceptionFormat) objectResponseEntity.getBody();

        //then
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
        assertEquals(objectResponseEntity.getStatusCode(), body.getStatus());
        assertTrue(body.getErrorMessage().containsKey(ERROR_MESSAGE_KEY));
        assertFalse(body.getErrorMessage().get(ERROR_MESSAGE_KEY).isEmpty());
        assertEquals(String.format("Request Parameters are invalid: %s", message), body.getErrorMessage().get(ERROR_MESSAGE_KEY));

    }

    @Test
    @DisplayName("Should return response entity with exception format relevant for AccessDeniedException")
    void should_return_response_entity_with_exception_format_relevant_for_access_denied_exception() {
        //when
        ResponseEntity<Object> objectResponseEntity = adviceHandler.handleAccessDeniedException();
        ExceptionFormat body = (ExceptionFormat) objectResponseEntity.getBody();

        //then
        assertEquals(HttpStatus.FORBIDDEN, objectResponseEntity.getStatusCode());
        assertEquals(objectResponseEntity.getStatusCode(), body.getStatus());
        assertTrue(body.getErrorMessage().containsKey(ERROR_MESSAGE_KEY));
        assertFalse(body.getErrorMessage().get(ERROR_MESSAGE_KEY).isEmpty());
        assertEquals("You have not sufficient grants to call this endpoint", body.getErrorMessage().get(ERROR_MESSAGE_KEY));

    }

    @Test
    @DisplayName("Should return response entity with exception format relevant for InternalException")
    void should_return_response_entity_with_exception_format_relevant_for_handle_internal_exception() {
        //given
        when(exception.getCause()).thenReturn(new InternalException("error"));

        //when
        ResponseEntity<Object> objectResponseEntity = adviceHandler.handleInternalException();
        ExceptionFormat body = (ExceptionFormat) objectResponseEntity.getBody();

        //then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, objectResponseEntity.getStatusCode());
        assertEquals(objectResponseEntity.getStatusCode(), body.getStatus());
        assertTrue(body.getErrorMessage().containsKey(ERROR_MESSAGE_KEY));
        assertFalse(body.getErrorMessage().get(ERROR_MESSAGE_KEY).isEmpty());
    }

    @Test
    @DisplayName("Should return response entity with exception format relevant for UserAlreadyConfirmedException")
    void should_return_response_entity_with_exception_format_relevant_for_handle_user_already_confirmed_exception() {
        //given
        when(exception.getCause()).thenReturn(new UserAlreadyConfirmedException());

        //when
        ResponseEntity<Object> objectResponseEntity = adviceHandler.handleUserAlreadyConfirmedException();
        ExceptionFormat body = (ExceptionFormat) objectResponseEntity.getBody();

        //then
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
        assertEquals(objectResponseEntity.getStatusCode(), body.getStatus());
        assertTrue(body.getErrorMessage().containsKey(ERROR_MESSAGE_KEY));
        assertFalse(body.getErrorMessage().get(ERROR_MESSAGE_KEY).isEmpty());
    }

    @Test
    @DisplayName("Should return response entity with exception format relevant for UserAlreadyConfirmedException")
    void should_return_response_entity_with_exception_format_relevant_for_handle_invalid_registration_token_exception() {
        //given
        when(exception.getCause()).thenReturn(new InvalidRegistrationTokenException());

        //when
        ResponseEntity<Object> objectResponseEntity = adviceHandler.handleInvalidRegistrationTokenException();
        ExceptionFormat body = (ExceptionFormat) objectResponseEntity.getBody();

        //then
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
        assertEquals(objectResponseEntity.getStatusCode(), body.getStatus());
        assertTrue(body.getErrorMessage().containsKey(ERROR_MESSAGE_KEY));
        assertFalse(body.getErrorMessage().get(ERROR_MESSAGE_KEY).isEmpty());
    }

    @Test
    @DisplayName("Should return response entity with exception format relevant for UserAlreadyConfirmedException")
    void should_return_response_entity_with_exception_format_relevant_for_handle_expired_registration_token_exception() {
        //given
        when(exception.getCause()).thenReturn(new ExpiredRegistrationTokenException());

        //when
        ResponseEntity<Object> objectResponseEntity = adviceHandler.handleExpiredRegistrationTokenException();
        ExceptionFormat body = (ExceptionFormat) objectResponseEntity.getBody();

        //then
        assertEquals(HttpStatus.UNAUTHORIZED, objectResponseEntity.getStatusCode());
        assertEquals(objectResponseEntity.getStatusCode(), body.getStatus());
        assertTrue(body.getErrorMessage().containsKey(ERROR_MESSAGE_KEY));
        assertFalse(body.getErrorMessage().get(ERROR_MESSAGE_KEY).isEmpty());
    }


}
