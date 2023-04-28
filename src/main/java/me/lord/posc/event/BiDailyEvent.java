package me.lord.posc.event;

import java.time.Duration;
import java.time.Instant;

public abstract class BiDailyEvent extends AbstractEvent {
	@Override
	public Instant getNextOccurrence() {
		return getDate().plus(Duration.ofHours(6L));
	}

	@Override
	public String getFrequency() {
		return "Bi-Daily";
	}
}
