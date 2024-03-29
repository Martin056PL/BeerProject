package wawer.kamil.beerproject.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@Getter
@Setter
@Component
public class ExceptionFormat {

    private String uuid;
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss[.SSSS]")
    private LocalDateTime timestamp;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Map<String, String> errorMessage;
}
