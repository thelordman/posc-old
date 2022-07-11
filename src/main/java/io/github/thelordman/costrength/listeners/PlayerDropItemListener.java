package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.items.Kit;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if(Kit.isFromKit(event.getItemDrop().getItemStack()) && event.getPlayer().getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);
    }
}