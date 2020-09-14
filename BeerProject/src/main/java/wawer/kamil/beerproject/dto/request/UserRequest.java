package wawer.kamil.beerproject.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@NoArgsConstructor
@Getter
public class UserRequest {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty
    private String email;
    private Set<String> grantedAuthorities;
}
