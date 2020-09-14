package wawer.kamil.beerproject.dto.response;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserResponse {

    private long id;
    private String username;
    private String password;
    private String email;
    private Set<String> grantedAuthorities;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
}
