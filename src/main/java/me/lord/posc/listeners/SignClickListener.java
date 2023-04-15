package me.lord.posc.listeners;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignClickListener implements Listener {

    @EventHandler
    public void onSignClick(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock() instanceof Sign sign) {
            event.getPlayer().openSign(sign);
        }
    }
}
