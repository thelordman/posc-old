package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PickupItemListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.getPlayer().sendActionBar(Methods.cStr("&cYou are not allowed to drop items!"));
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
            if(event.getAction().toString().toLowerCase().contains("drop")) {
                ((Player) event.getWhoClicked()).sendActionBar(Methods.cStr("&cYou are not allowed to drop items!"));
                event.setCancelled(true);
                return;
            }
        }
    }
}