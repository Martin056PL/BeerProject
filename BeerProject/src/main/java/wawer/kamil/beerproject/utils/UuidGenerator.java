package wawer.kamil.beerproject.utils;

import lombok.NoArgsConstructor;

import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UuidGenerator {
    public static String generateConfirmationToken() {
        return UUID.randomUUID().toString();
    }
}
