package io.github.thelordman.costrength.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.isCancelled())
            return;
        if(event.getItem() == null) {
            event.setCancelled(true);
        }
    }
}
