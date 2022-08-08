package io.github.thelordman.posc.quests.trigger;

/**
 * Class containing all existing triggers
 */
public final class TriggerType {
    /**
     * Triggers only using commands
     */
    public static final TriggerWrapper<Impossible> IMPOSSIBLE = new TriggerWrapper<>("impossible", Impossible.class);

    /**
     * Triggers every tick (20 times a second)
     */
    public static final TriggerWrapper<Tick> TICK = new TriggerWrapper<>("tick", Tick.class);

    private TriggerType() {

    }

}
