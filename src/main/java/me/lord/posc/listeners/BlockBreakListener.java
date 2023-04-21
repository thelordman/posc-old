package me.lord.posc.listeners;

import me.lord.posc.data.DataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!DataManager.getPlayerData(player).getEventData().getEvent().canBreak()) {
            event.setCancelled(true);
        }
    }
}
