package wawer.kamil.beerproject.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class UserRegistrationRequest {

    @Size(min = 8)
    @NotEmpty
    private final String username;
    @Size(min = 8)
    @NotEmpty
    private final String password;
    @Email
    private final String email;

}
