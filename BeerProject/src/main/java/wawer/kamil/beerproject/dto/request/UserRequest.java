package wawer.kamil.beerproject.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Getter
public class UserRequest {

    private String username;
    private String password;
    private String email;
    private Set<String> grantedAuthorities;
}
