package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().getItemMeta().hasDisplayName() && event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            if (event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(Methods.cStr("&7Backup Pickaxe"))) event.setCancelled(true);
        }
    }
}