package wawer.kamil.beerproject.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j(topic = "application.logger")
public class AdviceHandler extends ResponseEntityExceptionHandler {

    private final ExceptionFormat exceptionFormat;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> validationMap = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            validationMap.put(error.getField(), error.getDefaultMessage());
        }

        generateExceptionFormatProperties(validationMap, BAD_REQUEST);
        return ResponseEntity.badRequest().body(exceptionFormat);
    }

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<Object> notFoundHandler() {
        handleException(
                "Your item hasn't been found! Check request params!",
                NOT_FOUND
        );
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    @ExceptionHandler(InvalidImageParameters.class)
    public ResponseEntity<Object> badImageParameters() {
        handleException(
                "Your image has unhanded file type or it is over 10MB. Check again your image!",
                BAD_REQUEST
        );
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    @ExceptionHandler(RollbackException.class)
    public ResponseEntity<Object> rollbackException(RollbackException ex) {
        handleException(
                ex.getLocalizedMessage(),
                BAD_REQUEST
        );
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> usernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        handleException(
                "Username is unavailable",
                BAD_REQUEST
        );
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                       HttpHeaders headers,
                                                                       HttpStatus status,
                                                                       WebRequest request) {
        handleException(
                format("Request has not required parameter: %s", ex.getMessage()),
                BAD_REQUEST
        );
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<Object> invalidRequestParameterException(PropertyReferenceException ex) {
        handleException(
                format("Request Parameters are invalid: %s", ex.getMessage()),
                BAD_REQUEST
        );
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> AccessDeniedException(AccessDeniedException ex) {
        handleException(
                "You have not sufficient grants to call this endpoint",
                FORBIDDEN
        );
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> unknownException(Exception e, HttpServletRequest request) {
        log.error("APPLICATION THROWS EXCEPTION WITH ID: " + exceptionFormat.getUuid()
                + " AND " + e.getClass() + ",\n CAUSE OF EXCEPTION: " + e.getCause().toString()
                + ",\n EXCEPTION MESSAGE: " + e.getMessage()
                + ",\n EXCEPTION STACK TRACE: " + Arrays.toString(e.getStackTrace())
                + ",\n REQUEST ADDRESS: " + request.getRequestURL());
        String message = "Ups....Something goes wrong. Contact with administrator via github: https://github.com/Martin056PL";

        generateExceptionFormatProperties(getSingleErrorMessageMap(message), INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    private void handleException(String errorMessage, HttpStatus httpStatus) {
        setExceptionProperties(errorMessage, httpStatus);
        log.debug("Method throws this exception: {}", exceptionFormat);
    }

    private void setExceptionProperties(String errorMessage, HttpStatus httpStatus) {
        generateExceptionFormatProperties(getSingleErrorMessageMap(errorMessage), httpStatus);
    }

    private void generateExceptionFormatProperties(Map<String, String> errorsMessageMap, HttpStatus httpStatus) {
        exceptionFormat.setUuid(UUID.randomUUID().toString());
        exceptionFormat.setStatus(httpStatus);
        exceptionFormat.setTimestamp(LocalDateTime.now());
        exceptionFormat.setErrorMessage(errorsMessageMap);
    }

    private Map<String, String> getSingleErrorMessageMap(String message) {
        Map<String, String> map = new HashMap<>();
        map.put("error_message", message);
        return map;
    }
}
