package me.lord.posc.events;

import java.time.Duration;
import java.time.Instant;

public abstract class HourlyEvent implements Event {
    @Override
    public Instant getNextOccurrence() {
        return getDate().plus(Duration.ofHours(1L));
    }
}
