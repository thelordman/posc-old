package io.github.thelordman.posc.listeners;

import io.github.thelordman.posc.food.FoodManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerItemConsumeListener implements Listener {
    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        event.setCancelled(!FoodManager.consume(event.getPlayer(), event.getItem()));
    }
}