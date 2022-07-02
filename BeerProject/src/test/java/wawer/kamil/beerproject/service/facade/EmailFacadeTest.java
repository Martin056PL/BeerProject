package wawer.kamil.beerproject.service.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wawer.kamil.beerproject.model.email.Email;
import wawer.kamil.beerproject.model.email.factory.EmailFactory;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.service.EmailService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wawer.kamil.beerproject.helpers.EmailHelper.registrationNewUserEmail;
import static wawer.kamil.beerproject.helpers.UserTestHelper.getUserEntityWithHashedPassword;
import static wawer.kamil.beerproject.model.email.EmailType.REGISTRATION_CONFIRMATION;
import static wawer.kamil.beerproject.model.email.EmailType.UPDATE_REGISTRATION_TOKEN;

@ExtendWith(MockitoExtension.class)
class EmailFacadeTest {

    @Mock
    EmailService emailService;

    @Mock
    EmailFactory emailFactory;

    private User user;
    private Email testEmail;

    @InjectMocks
    EmailFacade emailFacade;


    @BeforeEach
    void setUp() {
        this.user = getUserEntityWithHashedPassword();
        this.testEmail = registrationNewUserEmail();
    }

    @Test
    @DisplayName("verify if email service sends an update email")
    void verify_if_email_service_sends_an_update_email() {
        //given
        when(emailFactory.getSpecificEmail(UPDATE_REGISTRATION_TOKEN, user)).thenReturn(testEmail);

        //when
        emailFacade.sendEmailWithUpdatedToken(user);

        //then
        verify(emailService).send(testEmail);
    }

    @Test
    @DisplayName("verify if email service sends an registration email")
    void verify_if_email_service_sends_an_registration_email() {
        //given
        when(emailFactory.getSpecificEmail(REGISTRATION_CONFIRMATION, user)).thenReturn(testEmail);

        //when
        emailFacade.sendRegistrationEmail(user);

        //then
        verify(emailService).send(testEmail);
    }


}
