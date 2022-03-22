package wawer.kamil.beerproject.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotBlank;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    private Set<String> grantedAuthorities;
}
