package wawer.kamil.beerproject.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class UserRegistrationRequest {

    @Size(min = 8)
    @NotEmpty
    private final String username;
    @Size(min = 8)
    @NotEmpty
    @ToString.Exclude()
    private final String password;
    @Email
    private final String email;

}
