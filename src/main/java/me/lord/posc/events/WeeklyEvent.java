package me.lord.posc.events;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;

public abstract class WeeklyEvent implements Event {
    @Override
    public Instant getNextOccurrence() {
        return getDate().plus(Duration.ofDays(7L));
    }

    public abstract DayOfWeek getDayOfWeek();
}
