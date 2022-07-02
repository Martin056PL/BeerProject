package wawer.kamil.beerproject.model.email;

import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.utils.EnvironmentProperties;

import static java.lang.String.format;

public class UpdateRegistrationUserTokenEmail extends Email implements LinkProvider {

    private final EnvironmentProperties env;

    public UpdateRegistrationUserTokenEmail(User user, EnvironmentProperties env) {
        super(user, "Refresh confirmation link", "no-reply@beerapp.com");
        this.env = env;
        this.content = generateEmailContent(user);
    }

    @Override
    public String generateEmailContent(User user) {
        String emailContent = getEmailTemplate("updateRegistrationTokenEmail.html");
        return format(
                emailContent,
                user.getUsername(),
                getLink(user.getUserRegistrationData().getConfirmationToken())
        );
    }

    @Override
    public String getLink(String... prams) {
        String userId = prams[0];
        String baseLink = env.getFullPathToApi() + "registration/refreshToken?id=%s";
        return format(baseLink, userId);
    }
}
