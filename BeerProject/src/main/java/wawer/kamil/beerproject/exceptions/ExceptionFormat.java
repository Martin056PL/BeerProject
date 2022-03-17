package wawer.kamil.beerproject.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor (access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
@Data
@Component
public class ExceptionFormat {

    private String uuid = String.valueOf(UUID.randomUUID());
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss[.SSSS]")
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
}
