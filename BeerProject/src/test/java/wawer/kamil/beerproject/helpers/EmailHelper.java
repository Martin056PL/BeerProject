package wawer.kamil.beerproject.helpers;

import wawer.kamil.beerproject.model.email.Email;
import wawer.kamil.beerproject.model.email.RegistrationUserConfirmationEmail;
import wawer.kamil.beerproject.model.user.User;

import static wawer.kamil.beerproject.helpers.UserTestHelper.getUserEntityWithUserRole;

public class EmailHelper {

    private static final User user = getUserEntityWithUserRole();

    public static Email registrationNewUserEmail(){
        return new TestEmail(user);
    }

}
