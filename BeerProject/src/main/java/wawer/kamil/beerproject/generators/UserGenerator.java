package wawer.kamil.beerproject.generators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.domain.User;
import wawer.kamil.beerproject.repositories.UserRepository;

@Component
@RequiredArgsConstructor
public class UserGenerator {

    private final UserRepository repository;

    //@EventListener(ApplicationReadyEvent.class)
    public void generateUsers(){

        User user1 = User.builder()
                .firstName("User")
                .lastName("Userowicz")
                .email("user.user@gmail.com")
                .password("user")
                .phoneNumber(123123123L)
                .isActive(true)
                .build();

        repository.save(user1);
    }

}
