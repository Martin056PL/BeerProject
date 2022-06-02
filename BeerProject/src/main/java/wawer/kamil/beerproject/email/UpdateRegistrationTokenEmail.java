package wawer.kamil.beerproject.email;

import wawer.kamil.beerproject.model.User;
import wawer.kamil.beerproject.utils.files.Files;

public class UpdateRegistrationTokenEmail extends Email {

    public UpdateRegistrationTokenEmail(User user) {
        setReceiver(user.getEmail());
        setSubject("Refresh confirmation link");
        setSender("no-reply@beerapp.com");
        setContent(generateEmailContent(user));
    }

    @Override
    public String generateEmailContent(User user) {
        String link = "http://localhost:8086/api/v1/registration/confirmRegistration?id=" + user.getId() + "&token=" + user.getUserRegistrationData().getConfirmationToken();
        String emailContent = Files.getEmailContent(FILE_PATH + "updateRegistrationTokenEmail.html");
        return String.format(emailContent, user.getUsername(), link);
    }
}
