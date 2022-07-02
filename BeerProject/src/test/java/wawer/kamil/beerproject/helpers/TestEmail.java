package wawer.kamil.beerproject.helpers;

import wawer.kamil.beerproject.model.email.Email;
import wawer.kamil.beerproject.model.user.User;

class TestEmail extends Email {

    public TestEmail(User user) {
        super(user, "test subject", "sender@email.com");
        this.setContent(generateEmailContent(user));
    }

    @Override
    public String generateEmailContent(User user) {
        return "test content";
    }
}
