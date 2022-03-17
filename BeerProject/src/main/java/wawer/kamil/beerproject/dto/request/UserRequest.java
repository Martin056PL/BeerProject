package wawer.kamil.beerproject.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserRequest {


    private String username;

    private String password;

    private String email;
    private Set<String> grantedAuthorities;
}
