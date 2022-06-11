package wawer.kamil.beerproject.helpers.model;

import wawer.kamil.beerproject.model.email.Email;
import wawer.kamil.beerproject.model.user.User;

public class TestEmail extends Email {
    @Override
    public String generateEmailContent(User user) {
        return null;
    }
}
