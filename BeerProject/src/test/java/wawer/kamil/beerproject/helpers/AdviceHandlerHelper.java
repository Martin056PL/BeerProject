package wawer.kamil.beerproject.helpers;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.persistence.RollbackException;
import java.util.List;

public class AdviceHandlerHelper {

    public static RollbackException getRollbackException() {
        return new RollbackException("Rollback exception");
    }

    public static MissingServletRequestParameterException getMissingServletRequestParameterException() {
        return new MissingServletRequestParameterException("sort", "asd");
    }

    public static List<FieldError> getFieldErrors(){
        return List.of(
                new FieldError("brewery", "name", "Name for brewery is required"),
                new FieldError("brewery", "email", "Email for brewery is required")
                );
    }
}
