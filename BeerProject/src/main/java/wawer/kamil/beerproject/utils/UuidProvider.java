package wawer.kamil.beerproject.utils;

import java.util.UUID;

public interface UuidProvider {
    UUID getUuid();

    class FakeUuidForTests implements UuidProvider {
        // ONLY FOR TEST PURPOSES!
        @Override
        public UUID getUuid() {
            return UUID.fromString("9280d346-e9af-11ec-8fea-0242ac120002");
        }
    }
}
