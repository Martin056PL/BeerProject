package wawer.kamil.beerproject.service.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import wawer.kamil.beerproject.model.registration.UserRegistrationData;
import wawer.kamil.beerproject.utils.TimeSetter;
import wawer.kamil.beerproject.utils.UuidProvider;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static wawer.kamil.beerproject.helpers.UserRegistrationHelper.getUserRegistrationDataWithoutConfirmedTokenAndDifferentUuid;

class UserRegistrationFacadeTest {

    private final TimeSetter timeSetter = new TimeSetter(Clock.fixed(Instant.parse("2022-01-01T20:20:20.000Z"), ZoneId.systemDefault()));

    private final UuidProvider uuidProvider = new UuidProvider.FakeUuidForTests();

    private final UserRegistrationFacade userRegistrationFacade = new UserRegistrationFacade(timeSetter, uuidProvider);

    private UserRegistrationData userRegistrationData;

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(userRegistrationFacade, "timeToTokenExpire", 15);
        this.userRegistrationData = getUserRegistrationDataWithoutConfirmedTokenAndDifferentUuid();
    }

    @Test
    @DisplayName("should update registration token")
    void should_update_registration_token(){
        //given
        assertNotEquals(userRegistrationData.getConfirmationToken(), uuidProvider.getUuid().toString());

        //when
        String newToken = userRegistrationFacade.refreshUserRegistrationToken(userRegistrationData);

        //then
        assertEquals(userRegistrationData.getConfirmationToken(), newToken);
    }
}
