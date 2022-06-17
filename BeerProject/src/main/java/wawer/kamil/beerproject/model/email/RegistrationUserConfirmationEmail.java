package wawer.kamil.beerproject.model.email;

import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.utils.files.Files;

public class RegistrationUserConfirmationEmail extends Email {

    public RegistrationUserConfirmationEmail(User user) {
        setReceiver(user.getEmail());
        setSubject("Registration confirmation");
        setSender("no-reply@beerapp.com");
        setContent(generateEmailContent(user));
    }

    @Override
    public String generateEmailContent(User user) {
        return Files.getEmailContent(FILE_PATH + "confirmRegistrationEmail.html");
    }
}
