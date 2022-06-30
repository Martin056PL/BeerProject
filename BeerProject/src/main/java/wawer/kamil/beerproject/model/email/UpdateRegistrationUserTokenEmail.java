package wawer.kamil.beerproject.model.email;

import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.utils.EnvironmentProperties;
import wawer.kamil.beerproject.utils.files.Files;

import static java.lang.String.format;

public class UpdateRegistrationUserTokenEmail extends Email implements LinkProvider {

    private final EnvironmentProperties env;
    private final User user;

    public UpdateRegistrationUserTokenEmail(User user, EnvironmentProperties env) {
        this.env = env;
        this.user = user;
        setReceiver(user.getEmail());
        setSubject("Refresh confirmation link");
        setSender("no-reply@beerapp.com");
        setContent(generateEmailContent(user));
    }

    @Override
    public String generateEmailContent(User user) {
        String emailContent = Files.getEmailContent(FILE_PATH + "updateRegistrationTokenEmail.html");
        return format(emailContent, user.getUsername(), getLink());
    }

    @Override
    public String getLink() {
        String baseLink = env.getServerUrlPrefix() + env.getContextPath() + "registration/refreshToken?id=%s";
        return format(baseLink, user.getId());
    }
}
