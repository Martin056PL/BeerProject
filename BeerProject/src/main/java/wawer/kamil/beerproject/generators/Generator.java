package wawer.kamil.beerproject.generators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.model.User;

import java.time.LocalDateTime;

import static wawer.kamil.beerproject.configuration.security.ApplicationUserRole.USER;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Generator {

    public static User createUser() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return new User(
                LocalDateTime.now(),
                "user",
                passwordEncoder.encode("user"),
                "kamil.wawer@pollub.edu.pl",
                USER.getGrantedAuthority(),
                true,
                true,
                true,
                true);
    }
}
