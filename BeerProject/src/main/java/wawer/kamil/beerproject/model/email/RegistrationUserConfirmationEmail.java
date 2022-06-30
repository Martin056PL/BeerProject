package wawer.kamil.beerproject.model.email;

import lombok.NoArgsConstructor;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.utils.EnvironmentProperties;
import wawer.kamil.beerproject.utils.files.Files;

import static java.lang.String.format;

@NoArgsConstructor
public class RegistrationUserConfirmationEmail extends Email implements LinkProvider {

    private EnvironmentProperties env;
    private User user;

    public RegistrationUserConfirmationEmail(User user, EnvironmentProperties env) {
        this.user = user;
        this.env = env;
        setReceiver(user.getEmail());
        setSubject("Registration confirmation");
        setSender("no-reply@beerapp.com");
        setContent(generateEmailContent(user));
    }

    @Override
    public String generateEmailContent(User user) {
        String emailContent = Files.getEmailContent(FILE_PATH + "confirmRegistrationEmail.html");
        return format(emailContent, user.getUsername(), getLink());
    }

    @Override
    public String getLink() {
        String baseLink = env.getServerUrlPrefix() + env.getContextPath() + "registration/confirmRegistration?id=%s&token=%s";
        return format(baseLink, user.getId(), user.getUserRegistrationData().getConfirmationToken());
    }
}
