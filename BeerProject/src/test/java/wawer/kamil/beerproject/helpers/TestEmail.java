package wawer.kamil.beerproject.helpers;

import wawer.kamil.beerproject.model.email.Email;
import wawer.kamil.beerproject.model.user.User;

class TestEmail extends Email {

    public TestEmail(User user) {
        setSender("sender@email.com");
        setReceiver("receiver@email.com");
        setSubject("test subject");
        setContent(generateEmailContent(user));

    }

    @Override
    public String generateEmailContent(User user) {
        return "test content";
    }
}
