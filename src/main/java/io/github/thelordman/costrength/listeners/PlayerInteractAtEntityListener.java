package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.utilities.Data;
import io.github.thelordman.costrength.utilities.GUIHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerInteractAtEntityListener implements Listener {
    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getName().equals("Sylvester")) GUIHandler.openGUI(Data.foodShopGUI, event.getPlayer());
    }
}