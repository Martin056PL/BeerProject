package wawer.kamil.beerproject.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TimeSetter {

    private final Clock clock;

    public LocalDateTime getNow() {
        return LocalDateTime.now(clock);
    }

}
