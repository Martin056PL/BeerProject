package wawer.kamil.beerproject.payloads;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ApiResponse {

    private final Boolean success;

    private final String message;

}
