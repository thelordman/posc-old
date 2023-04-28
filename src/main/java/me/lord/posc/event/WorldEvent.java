package me.lord.posc.event;

import me.lord.posc.Posc;
import me.lord.posc.data.DataManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

/**
 * Implement if Event has a world specifically made for it.
 */
public interface WorldEvent extends Event {
    static WorldEvent getCurrent() {
        return EventManager.getCurrentEvent() instanceof WorldEvent ? (WorldEvent) EventManager.getCurrentEvent() : null;
    }

    abstract class Data implements Event.Data {
        private final Player player;
        private final ItemStack[] oldInventory;

        Data(Player player) {
            this.player = player;
            oldInventory = player.getInventory().getStorageContents();
            player.getInventory().clear();
        }

        public ItemStack[] getOldInventory() {
            return oldInventory;
        }

        public Player getPlayer() {
            return player;
        }
    }

    World getWorld();

    @Override
    default boolean isJoinable() {
        return true;
    }

    default Location spawn() {
        return getWorld().getSpawnLocation();
    }

    @Override
    default Collection<? extends Player> getPlayers() {
        return getWorld().getPlayers();
    }

    @Override
    default void playerJoin(Player player) {
        Event.super.playerJoin(player);
        player.teleport(spawn());
    }

    @Override
    default void playerLeave(Player player) {
        Event.super.playerLeave(player);
        player.teleport(Posc.mainWorld.getSpawnLocation());
        Data data = (Data) DataManager.getPlayerData(player).getEventData();
        player.getInventory().setStorageContents(data.getOldInventory());
        DataManager.getPlayerData(player).setEventData(null);
    }
}
