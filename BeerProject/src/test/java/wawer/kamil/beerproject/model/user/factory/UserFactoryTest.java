package wawer.kamil.beerproject.model.user.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.model.registration.factory.UserRegistrationDataFactory;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.utils.TimeSetter;
import wawer.kamil.beerproject.utils.UuidProvider;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static wawer.kamil.beerproject.configuration.security.ApplicationUserRole.*;
import static wawer.kamil.beerproject.helpers.UserRegistrationHelper.getUserRegistrationRequest;
import static wawer.kamil.beerproject.helpers.UserTestHelper.*;

@ExtendWith(MockitoExtension.class)
class UserFactoryTest {

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Spy
    private TimeSetter timeSetter = new TimeSetter(Clock.fixed(Instant.parse("2022-01-01T20:20:20.000Z"), ZoneId.systemDefault()));

    @Spy
    private UuidProvider uuidProvider = new UuidProvider.FakeUuidForTests();

    @Spy
    private UserRegistrationDataFactory userRegistrationDataFactory = new UserRegistrationDataFactory(timeSetter, uuidProvider);

    @InjectMocks
    private UserFactory userFactory;

    private UserRegistrationRequest userRegistrationRequestWithUserRole;
    private User userCreatedViaUserFactoryWithUserRole;
    private User userCreatedViaUserFactoryWithExhibitorRole;
    private User userCreatedViaUserFactoryWithAdminRole;


    @BeforeEach
    void setUp() {
        this.userRegistrationRequestWithUserRole = getUserRegistrationRequest();
        this.userCreatedViaUserFactoryWithUserRole = getUserEntityWithHashedPassword();
        this.userCreatedViaUserFactoryWithExhibitorRole = getUserEntityWithHashedPasswordAndExhibitorRole();
        this.userCreatedViaUserFactoryWithAdminRole = getUserEntityWithHashedPasswordAndAdminRole();
        ReflectionTestUtils.setField(userRegistrationDataFactory, "timeToTokenExpire", 15);
    }

    @Test
    @DisplayName("should return user with user role")
    void should_return_user_with_user_role() {
        //given
        when(bCryptPasswordEncoder.encode("user")).thenReturn("hashed.password");

        //when
        User newUser = userFactory.createNewUser(userRegistrationRequestWithUserRole, USER);

        //then
        assertEquals(userCreatedViaUserFactoryWithUserRole.getId(), newUser.getId());
        assertEquals(userCreatedViaUserFactoryWithUserRole.getUsername(), newUser.getUsername());
        assertEquals(userCreatedViaUserFactoryWithUserRole.getPassword(), newUser.getPassword());
        assertEquals(userCreatedViaUserFactoryWithUserRole.getEmail(), newUser.getEmail());
        assertEquals(userCreatedViaUserFactoryWithUserRole.getGrantedAuthorities(), newUser.getGrantedAuthorities());
        assertEquals(
                userCreatedViaUserFactoryWithUserRole.getUserRegistrationData().getExpiryDate(),
                newUser.getUserRegistrationData().getExpiryDate()
        );
        assertEquals(
                userCreatedViaUserFactoryWithUserRole.getUserRegistrationData().getConfirmationToken(),
                newUser.getUserRegistrationData().getConfirmationToken()
        );
        assertEquals(
                userCreatedViaUserFactoryWithUserRole.getUserRegistrationData().isConfirmed(),
                newUser.getUserRegistrationData().isConfirmed()
        );
    }

    @Test
    @DisplayName("should return user with exhibitor role")
    void should_return_user_with_exhibitor_role() {
        //given
        when(bCryptPasswordEncoder.encode("user")).thenReturn("hashed.password");

        //when
        User newUser = userFactory.createNewUser(userRegistrationRequestWithUserRole, EXHIBITOR);

        //then
        assertEquals(userCreatedViaUserFactoryWithExhibitorRole.getId(), newUser.getId());
        assertEquals(userCreatedViaUserFactoryWithExhibitorRole.getUsername(), newUser.getUsername());
        assertEquals(userCreatedViaUserFactoryWithExhibitorRole.getPassword(), newUser.getPassword());
        assertEquals(userCreatedViaUserFactoryWithExhibitorRole.getEmail(), newUser.getEmail());
        assertEquals(userCreatedViaUserFactoryWithExhibitorRole.getGrantedAuthorities(), newUser.getGrantedAuthorities());
        assertEquals(
                userCreatedViaUserFactoryWithExhibitorRole.getUserRegistrationData().getExpiryDate(),
                newUser.getUserRegistrationData().getExpiryDate()
        );
        assertEquals(
                userCreatedViaUserFactoryWithExhibitorRole.getUserRegistrationData().getConfirmationToken(),
                newUser.getUserRegistrationData().getConfirmationToken()
        );
        assertEquals(
                userCreatedViaUserFactoryWithUserRole.getUserRegistrationData().isConfirmed(),
                newUser.getUserRegistrationData().isConfirmed()
        );
    }

    @Test
    @DisplayName("should return user with admin role")
    void should_return_user_with_admin_role() {
        //given
        when(bCryptPasswordEncoder.encode("user")).thenReturn("hashed.password");

        //when
        User newUser = userFactory.createNewUser(userRegistrationRequestWithUserRole, ADMIN);

        //then
        assertEquals(userCreatedViaUserFactoryWithAdminRole.getId(), newUser.getId());
        assertEquals(userCreatedViaUserFactoryWithAdminRole.getUsername(), newUser.getUsername());
        assertEquals(userCreatedViaUserFactoryWithAdminRole.getPassword(), newUser.getPassword());
        assertEquals(userCreatedViaUserFactoryWithAdminRole.getEmail(), newUser.getEmail());
        assertEquals(userCreatedViaUserFactoryWithAdminRole.getGrantedAuthorities(), newUser.getGrantedAuthorities());
        assertEquals(
                userCreatedViaUserFactoryWithAdminRole.getUserRegistrationData().getExpiryDate(),
                newUser.getUserRegistrationData().getExpiryDate()
        );
        assertEquals(
                userCreatedViaUserFactoryWithAdminRole.getUserRegistrationData().getConfirmationToken(),
                newUser.getUserRegistrationData().getConfirmationToken()
        );
        assertEquals(
                userCreatedViaUserFactoryWithUserRole.getUserRegistrationData().isConfirmed(),
                newUser.getUserRegistrationData().isConfirmed()
        );
    }
}
