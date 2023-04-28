package me.lord.posc.event;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;

public abstract class WeeklyEvent extends AbstractEvent {
    @Override
    public Instant getNextOccurrence() {
        return getDate().plus(Duration.ofDays(7L));
    }

    @Override
    public String getFrequency() {
        return "Weekly";
    }

    public abstract DayOfWeek getDayOfWeek();
}
