package me.lord.posc.events;

import java.time.Instant;

public abstract class AbstractEvent implements Event {
    protected Instant date;
    protected boolean hasEnded;

    public AbstractEvent() {
        date = Instant.now();
        hasEnded = false;
    }

    @Override
    public Instant getDate() {
        return date;
    }

    @Override
    public void end() {
        hasEnded = true;
    }
}
