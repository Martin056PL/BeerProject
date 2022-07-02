package wawer.kamil.beerproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import wawer.kamil.beerproject.dto.request.UserRegistrationRequest;
import wawer.kamil.beerproject.dto.response.UserRegistrationResponse;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.service.facade.EmailFacade;
import wawer.kamil.beerproject.service.facade.UserRegistrationFacade;
import wawer.kamil.beerproject.service.impl.RegistrationUserServiceImpl;
import wawer.kamil.beerproject.service.impl.UserServiceImpl;
import wawer.kamil.beerproject.utils.UuidProvider;
import wawer.kamil.beerproject.utils.mapper.UserRegistrationMapper;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static wawer.kamil.beerproject.configuration.security.ApplicationUserRole.USER;
import static wawer.kamil.beerproject.helpers.UserRegistrationHelper.getUserRegistrationRequest;
import static wawer.kamil.beerproject.helpers.UserRegistrationHelper.getUserRegistrationResponse;
import static wawer.kamil.beerproject.helpers.UserTestHelper.getDisabledUserEntity;
import static wawer.kamil.beerproject.helpers.UserTestHelper.getUserEntityWithHashedPassword;

@ExtendWith(MockitoExtension.class)
class RegistrationUserServiceImplTest {

    @Mock
    UserRegistrationFacade userRegistrationFacade;

    @Mock
    EmailFacade emailFacade;

    @Spy
    UserRegistrationMapper userRegistrationMapper = new UserRegistrationMapper(new ModelMapper());

    UuidProvider uuidProvider = new UuidProvider.FakeUuidForTests();

    @Spy
    Clock clock;

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    RegistrationUserServiceImpl registrationUserService;

    private UserRegistrationRequest request;
    private UserRegistrationResponse userRegistrationResponse;

    private User userWithRegistrationData;
    private User savedUserWithRegistrationData;

    private static final Long ID = 1L;

    @BeforeEach
    void setUp() {
        this.request = getUserRegistrationRequest();
        this.userRegistrationResponse = getUserRegistrationResponse();
        this.userWithRegistrationData = getUserEntityWithHashedPassword();
        this.savedUserWithRegistrationData = getDisabledUserEntity();
    }

    @Test
    @DisplayName("should return user registration response")
    void should_return_user_registration_response() {
        //given
        when(userService.getUserWithUserRegistrationData(request, USER)).thenReturn(userWithRegistrationData);
        when(userService.saveUser(userWithRegistrationData)).thenReturn(savedUserWithRegistrationData);
        doNothing().when(emailFacade).sendRegistrationEmail(savedUserWithRegistrationData);

        //when
        UserRegistrationResponse response = registrationUserService.registerNewUser(request);

        //then
        assertEquals(userRegistrationResponse.getId(), response.getId());
    }

    @Test
    @DisplayName("should generate new uuid as registration token")
    void should_generate_new_uuid_as_registration_token() {
        //given
        when(userService.findUserById(ID)).thenReturn(savedUserWithRegistrationData);
        when(userRegistrationFacade.refreshUserRegistrationToken(savedUserWithRegistrationData.getUserRegistrationData()))
                .thenReturn(uuidProvider.getUuid().toString());
        doNothing().when(emailFacade).sendEmailWithUpdatedToken(savedUserWithRegistrationData);

        //when
        String response = registrationUserService.refreshRegistrationToken(ID);

        //then
        assertEquals(uuidProvider.getUuid().toString(), response);
    }

    @Test
    @DisplayName("should enable user when token is correct")
    void should_enable_user_when_token_is_correct() {
        //given
        when(userService.findUserById(ID)).thenReturn(savedUserWithRegistrationData);
        when(clock.instant()).thenReturn(Instant.parse("2022-01-01T20:20:20.000Z"));
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        doCallRealMethod().when(userService).enableUserAccount(savedUserWithRegistrationData);

        //when
        registrationUserService.confirmCredentials(ID, uuidProvider.getUuid().toString());

        //then
        assertTrue(savedUserWithRegistrationData.isEnabled());
        assertTrue(savedUserWithRegistrationData.getUserRegistrationData().isConfirmed());
    }
}
