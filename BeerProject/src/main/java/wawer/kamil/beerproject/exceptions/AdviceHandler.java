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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

        String mapToString = convertMapToEditedString(validationMap);
        exceptionFormat.setStatus(HttpStatus.OK);
        exceptionFormat.setMessage(mapToString);
        return ResponseEntity.badRequest().body(exceptionFormat);
    }

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<Object> notFoundHandler() {
        exceptionFormat.setMessage("Your item hasn't been found! Check request params!");
        exceptionFormat.setStatus(HttpStatus.NOT_FOUND);
        log.debug("Method throws this exception: {}", exceptionFormat);
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    @ExceptionHandler(InvalidImageParameters.class)
    public ResponseEntity<Object> badImageParameters() {
        exceptionFormat.setMessage("Your image has unhanded file type or it is over 10MB. Check again your image!");
        exceptionFormat.setStatus(HttpStatus.BAD_REQUEST);
        log.debug("Method throws this exception: {}", exceptionFormat);
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    @ExceptionHandler(RollbackException.class)
    public ResponseEntity<Object> rollbackException(RollbackException ex) {
        exceptionFormat.setMessage(ex.getLocalizedMessage());
        exceptionFormat.setStatus(HttpStatus.BAD_REQUEST);
        log.debug("Method throws this exception: {}", exceptionFormat);
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> usernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        exceptionFormat.setMessage("Username is unavailable");
        exceptionFormat.setStatus(HttpStatus.BAD_REQUEST);
        log.debug("Method throws this exception: {}", exceptionFormat);
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<Object> invalidRequestParameter(PropertyReferenceException ex){
        exceptionFormat.setMessage(String.format("Request Parameters are invalid: %s", ex.getMessage()));
        exceptionFormat.setStatus(HttpStatus.BAD_REQUEST);
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
        exceptionFormat.setMessage("Ups....Something goes wrong. Contact with administrator via github: https://github.com/Martin056PL");
        exceptionFormat.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    private String convertMapToEditedString(Map<String, String> map) {
        String string1 = map.toString().replace(", ", ",");
        String string2 = string1.replace(",", "\n");
        return string2.substring(1, string2.length() - 1);
    }
}
