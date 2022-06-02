package wawer.kamil.beerproject.email;


import wawer.kamil.beerproject.model.User;
import wawer.kamil.beerproject.utils.files.Files;

public class RegistrationConfirmationEmail extends Email {

    public RegistrationConfirmationEmail(User user) {
        setReceiver(user.getEmail());
        setSubject("Registration confirmation");
        setSender("no-reply@beerapp.com");
        setContent(generateEmailContent(user));
    }

    @Override
    public String generateEmailContent(User user) {
        String link = "http://localhost:8086/api/v1/registration/confirmRegistration?id=" + user.getId() + "&token=" + user.getUserRegistrationData().getConfirmationToken();
        String emailContent = Files.getEmailContent(FILE_PATH + "confirmRegistrationEmail.html");
        return String.format(emailContent, user.getUsername(), link);
    }
}
