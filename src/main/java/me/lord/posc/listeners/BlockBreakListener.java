package me.lord.posc.listeners;

import me.lord.posc.data.DataManager;
import me.lord.posc.event.Event;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();

		Event.Data eventData = DataManager.getPlayerData(player).getEventData();
		if (eventData != null) {
			if (!eventData.getEvent().canBreak()) {
				event.setCancelled(true);
			}
		}
	}
}
