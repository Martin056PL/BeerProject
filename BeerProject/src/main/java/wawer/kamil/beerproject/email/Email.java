package wawer.kamil.beerproject.email;

import lombok.Getter;
import lombok.Setter;
import wawer.kamil.beerproject.model.User;

@Getter
@Setter
public abstract class Email {
    String receiver;
    String sender;
    String subject;
    String content;

    protected static final String FILE_PATH = "src/main/resources/static/";

    public abstract String generateEmailContent(User user);
}
