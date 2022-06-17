package wawer.kamil.beerproject.model.email;

import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.utils.files.Files;

public class UpdateRegistrationUserTokenEmail extends Email {

    public UpdateRegistrationUserTokenEmail(User user) {
        setReceiver(user.getEmail());
        setSubject("Refresh confirmation link");
        setSender("no-reply@beerapp.com");
        setContent(generateEmailContent(user));
    }

    @Override
    public String generateEmailContent(User user) {
        return Files.getEmailContent(FILE_PATH + "updateRegistrationTokenEmail.html");
    }
}
