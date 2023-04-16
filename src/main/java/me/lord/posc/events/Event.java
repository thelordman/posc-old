package me.lord.posc.events;

import java.time.Duration;
import java.time.Instant;

public interface Event {

    void initiate();

    Instant getDate();

    Duration getDuration();

    Instant getNextOccurrence();

    boolean hasEnded();

    void end();
}
