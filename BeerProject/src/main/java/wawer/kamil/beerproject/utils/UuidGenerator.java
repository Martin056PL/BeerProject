package wawer.kamil.beerproject.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidGenerator implements UuidProvider {

    @Override
    public UUID getUuid() {
        return UUID.randomUUID();
    }
}
