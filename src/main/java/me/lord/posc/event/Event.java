package me.lord.posc.event;

import me.lord.posc.data.DataManager;
import me.lord.posc.utilities.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.function.Consumer;

public interface Event {
    static Event getCurrent() {
        return EventManager.getCurrentEvent();
    }

    static Event[] getAll() {
        return EventManager.getEvents();
    }

    static int getTotalAmount() {
        return EventManager.getEventAmount();
    }

    static Event fromClass(Class<? extends Event> c) {
        for (Event event : EventManager.getEvents()) {
            if (event.getClass() == c) {
                return event;
            }
        }
        return null;
    }

    static Event fromName(String name) {
        for (Event event : EventManager.getEvents()) {
            if (event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }

    interface Data {
        Event getEvent();
    }

    default Runnable task() {
        return null;
    }

    default Consumer<Player> forEachPlayer() {
        return null;
    }

    default void start() {
        EventManager.setCurrentEvent(this);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.showTitle(Title.title(TextUtil.c("&6" + TextUtil.sexyString(getName()) + " Event"), TextUtil.c("&f/event join")));
            Component component = TextUtil.c("\n&e&l" + getFrequency() + " Event: &6&l" + TextUtil.sexyString(getName()) + (isJoinable() ? "\n&6&nClick To Join" : ""));
            if (isJoinable()) component.clickEvent(ClickEvent.runCommand("/event join"));
            player.sendMessage(component);
        }
    }

    default Collection<? extends Player> getPlayers() {
        return Bukkit.getOnlinePlayers();
    }

    default boolean isGodMode() {
        return false;
    }

    default boolean canBreak() {
        return true;
    }

    default boolean canPlace() {
        return true;
    }

    default boolean canHit() {
        return true;
    }

    default boolean canPush() {
        return true;
    }

    default boolean canMoveItems() {
        return true;
    }

    default String getName() {
        return this.getClass().getAnnotation(EventName.class).name();
    }

    String getFrequency();

    default boolean isJoinable() {
        return false;
    }

    default void playerJoin(Player player) {
        if (!isJoinable()) {
            throw new IllegalStateException("Tried to make a player join an event that is not joinable");
        }
        if (!canPush()) {
            player.setCollidable(false);
        }
        if (isGodMode()) {
            DataManager.getPlayerData(player).setGodMode(true);
        }
    }

    default void playerLeave(Player player) {
        if (!isJoinable()) {
            throw new IllegalStateException("Tried to make a player leave an event that is not joinable");
        }
        if (!canPush()) {
            player.setCollidable(true);
        }
        if (isGodMode()) {
            DataManager.getPlayerData(player).setGodMode(false);
        }
        player.setGameMode(GameMode.SURVIVAL);
    }

    Instant getDate();

    default Duration getDuration() {
        return Duration.ofHours(1L);
    }

    default Instant getEnd() {
        return getDate().plus(getDuration());
    }

    Instant getNextOccurrence();

    boolean hasStarted();

    default boolean hasEnded() {
        return !hasStarted();
    }

    void end();
}
