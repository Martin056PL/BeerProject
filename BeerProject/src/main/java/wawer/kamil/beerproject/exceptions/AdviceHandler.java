package wawer.kamil.beerproject.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j(topic = "application.logger")
public class AdviceHandler extends ResponseEntityExceptionHandler {

    private final ExceptionFormat exceptionFormat;

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<Object> notFoundHandler(){
        exceptionFormat.setMessage("Your content haven't been found! Check request params!");
        exceptionFormat.setStatus(HttpStatus.NOT_FOUND);
        log.debug("Feign Client throws this exception: {}", exceptionFormat);
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> unknownException(Exception e, HttpServletRequest request){
        log.error("APPLICATION THROWS EXCEPTION: " + e.getClass() + ",\n CAUSE OF EXCEPTION: "
                + e.getCause().toString() + ",\n EXCEPTION MESSAGE: " + e.getMessage()
                + ",\n EXCEPTION STACK TRACE: " + Arrays.toString(e.getStackTrace())
                + "\n REQUEST ADDRESS: " + request.getRequestURL());
        exceptionFormat.setMessage("Ups.... Something goes wrong...");
        exceptionFormat.setStatus(HttpStatus.I_AM_A_TEAPOT);
        return new ResponseEntity<>(exceptionFormat, exceptionFormat.getStatus());
    }
}
