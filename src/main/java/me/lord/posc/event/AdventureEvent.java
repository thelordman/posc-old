package me.lord.posc.event;

public interface AdventureEvent extends Event {
	@Override
	default boolean canBreak() {
		return false;
	}

	@Override
	default boolean canPlace() {
		return false;
	}
}
