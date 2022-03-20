package wawer.kamil.beerproject.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.RollbackException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

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

        generateExceptionFormatProperties(null, validationMap, BAD_REQUEST);
        return ResponseEntity.badRequest().body(exceptionFormat);
    }

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<Object> notFoundHandler() {
        String message = "Your item hasn't been found! Check request params!";
        generateExceptionFormatProperties(message,null, NOT_FOUND);
        log.debug("Method throws this exception: {}", exceptionFormat);
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    @ExceptionHandler(InvalidImageParameters.class)
    public ResponseEntity<Object> badImageParameters() {
        String message = "Your image has unhanded file type or it is over 10MB. Check again your image!";
        generateExceptionFormatProperties(message, null, BAD_REQUEST);
        log.debug("Method throws this exception: {}", exceptionFormat);
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    @ExceptionHandler(RollbackException.class)
    public ResponseEntity<Object> rollbackException(RollbackException ex) {
        generateExceptionFormatProperties(ex.getLocalizedMessage(), null, BAD_REQUEST);
        log.debug("Method throws this exception: {}", exceptionFormat);
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> usernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        String message = "Username is unavailable";
        generateExceptionFormatProperties(message,null, BAD_REQUEST);
        log.debug("Method throws this exception: {}", exceptionFormat);
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<Object> invalidRequestParameter(PropertyReferenceException ex){
        String message = String.format("Request Parameters are invalid: %s", ex.getMessage());
        generateExceptionFormatProperties(message,null, BAD_REQUEST);
        log.debug("Method throws this exception: {}", exceptionFormat);
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

        generateExceptionFormatProperties(message,null, INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    private void generateExceptionFormatProperties(String message, Map<String,String> validationErrorMap, HttpStatus httpStatus) {
        exceptionFormat.setUuid(UUID.randomUUID().toString());
        exceptionFormat.setStatus(httpStatus);
        exceptionFormat.setTimestamp(LocalDateTime.now());
        Map<String, String> stringStringMap = Optional.ofNullable(validationErrorMap).orElseGet(() -> asd(message));
        exceptionFormat.setError_message(stringStringMap);
    }

    private Map<String, String> asd(String message) {
        Map<String, String> map = new HashMap<>();
        map.put("error_message", message);
        return map;
    }
}
