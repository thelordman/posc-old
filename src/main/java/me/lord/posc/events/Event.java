package me.lord.posc.events;

import java.time.Duration;
import java.time.Instant;

public interface Event {
    Instant getDate();

    default Duration getDuration() {
        return Duration.ofHours(1L);
    }

    default Instant getEnd() {
        return getDate().plus(getDuration());
    }

    Instant getNextOccurrence();

    boolean hasEnded();

    void end();
}
