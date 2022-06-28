package wawer.kamil.beerproject.model.email.decorator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wawer.kamil.beerproject.model.email.Email;
import wawer.kamil.beerproject.model.email.EmailType;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.utils.link.LinkFactory;
import wawer.kamil.beerproject.utils.link.LinkProvider;

@RequiredArgsConstructor
@Component
public class EmailDecorator {

    private final LinkFactory factory;

    public Email getEmailWithLink(Email email, User user, EmailType emailType) {
        return new EmailWithLink(email, user, getLinkProvider(emailType));
    }

    private LinkProvider getLinkProvider(EmailType emailType) {
        return factory.getLinkProvider(emailType);
    }
}
