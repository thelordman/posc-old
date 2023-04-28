package me.lord.posc.event;

public interface LimitedEvent extends AdventureEvent {
    @Override
    default boolean isGodMode() {
        return true;
    }

    @Override
    default boolean canHit() {
        return false;
    }

    @Override
    default boolean canPush() {
        return false;
    }

    @Override
    default boolean canMoveItems() {
        return false;
    }

    @Override
    default boolean hunger() {
        return false;
    }
}
