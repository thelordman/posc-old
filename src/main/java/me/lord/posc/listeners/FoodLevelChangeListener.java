package me.lord.posc.listeners;

import me.lord.posc.data.DataManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        HumanEntity humanEntity = event.getEntity();

        if (humanEntity instanceof Player player) {
            if (!DataManager.getPlayerData(player).hunger()) {
                player.setFoodLevel(20);
                player.setSaturation(5.0F);
                event.setCancelled(true);
            }
        }
    }
}
