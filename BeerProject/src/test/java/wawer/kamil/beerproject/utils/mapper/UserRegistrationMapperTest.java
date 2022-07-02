package wawer.kamil.beerproject.utils.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import wawer.kamil.beerproject.dto.response.UserRegistrationResponse;
import wawer.kamil.beerproject.model.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static wawer.kamil.beerproject.helpers.UserRegistrationHelper.getUserRegistrationResponse;
import static wawer.kamil.beerproject.helpers.UserTestHelper.getUserEntityWithUserRole;

@ExtendWith(MockitoExtension.class)
class UserRegistrationMapperTest {

    private final UserRegistrationMapper userRegistrationMapper = new UserRegistrationMapper(new ModelMapper());
    private User user;
    private UserRegistrationResponse userRegistrationResponse;

    @BeforeEach
    void setUp(){
        this.user = getUserEntityWithUserRole();
        this.userRegistrationResponse = getUserRegistrationResponse();
    }

    @Test
    @DisplayName("asd")
    void asd(){
        UserRegistrationResponse response = userRegistrationMapper.mapUserRegistrationEntityToUserRegistrationResponse(user);
        assertEquals(userRegistrationResponse.getId(), response.getId());
        assertEquals(userRegistrationResponse.getUsername(), response.getUsername());
        assertEquals(userRegistrationResponse.getEmail(), response.getEmail());
    }
}
