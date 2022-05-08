package io.github.thelordman.costrength.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryDragListener implements Listener {
    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getView().getTitle().equals("Food Shop") | event.getView().getTitle().equals("Kitchen")) event.setCancelled(true);
    }
}
