package wawer.kamil.beerproject.model.email.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wawer.kamil.beerproject.model.email.Email;
import wawer.kamil.beerproject.model.email.RegistrationUserConfirmationEmail;
import wawer.kamil.beerproject.model.email.UpdateRegistrationUserTokenEmail;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.utils.EnvironmentProperties;

import static wawer.kamil.beerproject.helpers.UserTestHelper.getUserEntityWithUserRole;
import static wawer.kamil.beerproject.model.email.EmailType.REGISTRATION_CONFIRMATION;
import static wawer.kamil.beerproject.model.email.EmailType.UPDATE_REGISTRATION_TOKEN;

@ExtendWith(MockitoExtension.class)
class EmailFactoryTest {

    @Mock
    EnvironmentProperties environmentProperties;

    @InjectMocks
    EmailFactory emailFactory;

    private User user;

    @BeforeEach
    void setUp() {
        this.user = getUserEntityWithUserRole();
    }

    @Test
    @DisplayName("Should return registration base email")
    void should_return_registration_email() {
        //given
        //when
        Email email = emailFactory.getSpecificEmail(REGISTRATION_CONFIRMATION, user);

        //then
        Assertions.assertEquals(RegistrationUserConfirmationEmail.class, email.getClass());
    }

    @Test
    @DisplayName("Should return registration base email")
    void should_return_registration_base_email() {
        //given
        //when
        Email email = emailFactory.getSpecificEmail(UPDATE_REGISTRATION_TOKEN, user);

        //then
        Assertions.assertEquals(UpdateRegistrationUserTokenEmail.class, email.getClass());

    }

}
