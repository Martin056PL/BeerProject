package wawer.kamil.beerproject.exceptions;

import lombok.*;

import java.util.Map;


@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResponse extends ExceptionFormat{

    private Map<String,String> invalidFields;

}
