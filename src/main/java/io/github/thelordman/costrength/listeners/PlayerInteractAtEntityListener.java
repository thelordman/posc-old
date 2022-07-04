package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.guis.GUIHandler;
import io.github.thelordman.costrength.utilities.data.Data;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerInteractAtEntityListener implements Listener {
    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (event.getRightClicked().getName().equals("Sylvester")) GUIHandler.openGUI(Data.GUIs[0], event.getPlayer());
        if (event.getRightClicked().getName().equals("Claus")) GUIHandler.openGUI(Data.GUIs[2], event.getPlayer());
    }
}