package wawer.kamil.beerproject.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRegistrationResponse {

    private Long id;
    private String username;
    private String email;

}
