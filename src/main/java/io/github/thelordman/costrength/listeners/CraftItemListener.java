package io.github.thelordman.costrength.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import java.util.Objects;

public class CraftItemListener implements Listener {
    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        if (Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.SUGAR)) event.setCancelled(true);
    }
}
