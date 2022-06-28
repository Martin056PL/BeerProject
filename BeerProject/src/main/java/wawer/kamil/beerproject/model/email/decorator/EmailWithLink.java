package wawer.kamil.beerproject.model.email.decorator;

import wawer.kamil.beerproject.model.email.Email;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.utils.link.LinkProvider;

public class EmailWithLink extends Email {

    private final Email email;
    private final LinkProvider linkProvider;

    public EmailWithLink(Email email, User user, LinkProvider linkProvider) {
        this.email = email;
        this.linkProvider = linkProvider;
        bindProperties(email, user);
    }

    @Override
    public String generateEmailContent(User user) {
        String linkPattern = linkProvider.getLink();
        String link = String.format(linkPattern, user.getId(), user.getUserRegistrationData().getConfirmationToken());
        return String.format(email.generateEmailContent(user), user.getUsername(), link);
    }
}
