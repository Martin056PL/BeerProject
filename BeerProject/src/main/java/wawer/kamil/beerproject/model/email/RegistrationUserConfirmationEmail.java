package wawer.kamil.beerproject.model.email;

import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.utils.EnvironmentProperties;

import static java.lang.String.format;

public class RegistrationUserConfirmationEmail extends Email implements LinkProvider {

    private final EnvironmentProperties env;

    public RegistrationUserConfirmationEmail(User user, EnvironmentProperties env) {
        super(user, "Registration confirmation", "no-reply@beerapp.com");
        this.env = env;
        this.content = generateEmailContent(user);
    }

    @Override
    public String generateEmailContent(User user) {
        String emailContent = getEmailTemplate("confirmRegistrationEmail.html");
        return format(
                emailContent,
                user.getUsername(),
                getLink(
                        String.valueOf(user.getId()),
                        user.getUserRegistrationData().getConfirmationToken()
                )
        );
    }

    @Override
    public String getLink(String... params) {
        String userId = params[0];
        String userConfirmationToken = params[1];
        String baseLink = env.getFullPathToApi() + "registration/confirmRegistration?id=%s&token=%s";
        return format(baseLink, userId, userConfirmationToken);
    }
}
