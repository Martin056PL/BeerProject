package wawer.kamil.beerproject.model.email;

import lombok.Getter;
import lombok.Setter;
import wawer.kamil.beerproject.model.user.User;
import wawer.kamil.beerproject.utils.files.Files;

@Getter
@Setter
public abstract class Email {
    protected String receiver;
    protected String sender;
    protected String subject;
    protected String content;

    protected Email(User user, String subject, String sender){
        this.receiver = user.getEmail();
        this.subject = subject;
        this.sender = sender;
    }

    public abstract String generateEmailContent(User user);

    public String getEmailTemplate(String templateName){
        return Files.getEmailContent(templateName);
    }
}
