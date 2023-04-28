package me.lord.posc.event;

import java.time.*;

public abstract class SeasonalEvent extends AbstractEvent {
	public abstract MonthDay getStartDay();

	public abstract MonthDay getEndDay();

	@Override
	public String getFrequency() {
		return "Seasonal";
	}

	@Override
	public Duration getDuration() {
		return Duration.ofDays(getEndDay().getDayOfMonth() - getStartDay().getDayOfMonth() + 1L);
	}

	@Override
	public Instant getNextOccurrence() {
		LocalDateTime nextOccurrence = getStartDay().atYear(OffsetDateTime.now().getYear()).atTime(0, 0);
		if (!nextOccurrence.isAfter(LocalDateTime.now(Clock.systemUTC()))) {
			return Instant.from(nextOccurrence.plusYears(1L));
		}
		return Instant.from(nextOccurrence);
	}
}
