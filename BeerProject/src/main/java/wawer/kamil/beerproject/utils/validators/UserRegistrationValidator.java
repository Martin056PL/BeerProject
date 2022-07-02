package wawer.kamil.beerproject.utils.validators;

import wawer.kamil.beerproject.model.registration.UserRegistrationData;

import java.time.Clock;
import java.util.function.Function;

import static java.time.LocalDateTime.now;
import static wawer.kamil.beerproject.utils.validators.UserRegistrationValidator.ValidationResult;
import static wawer.kamil.beerproject.utils.validators.UserRegistrationValidator.ValidationResult.*;

public interface UserRegistrationValidator extends Function<UserRegistrationData, ValidationResult> {

    static UserRegistrationValidator isTokenConfirmed(){
        return urd -> urd.isConfirmed() ? TOKEN_ALREADY_CONFIRMED : SUCCESS ;
    }

    static UserRegistrationValidator isTokenExpired(Clock clock){
        return urd -> now(clock).isAfter(urd.getExpiryDate()) ? TOKEN_EXPIRED : SUCCESS;
    }

    static UserRegistrationValidator isTokenMatches(String token){
        return urd -> token.equals(urd.getConfirmationToken()) ? SUCCESS : TOKEN_DOES_NOT_MATCH;
    }

    default UserRegistrationValidator and(UserRegistrationValidator other) {
        return urd -> {
            ValidationResult result = this.apply(urd);
            return result.equals(SUCCESS) ? other.apply(urd) : result;
        };
    }

    enum ValidationResult {
        SUCCESS,
        TOKEN_EXPIRED,
        TOKEN_ALREADY_CONFIRMED,
        TOKEN_DOES_NOT_MATCH
    }
}