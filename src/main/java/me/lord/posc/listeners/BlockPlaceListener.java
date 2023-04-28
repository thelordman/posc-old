package me.lord.posc.listeners;

import me.lord.posc.data.DataManager;
import me.lord.posc.event.Event;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();

		Event.Data eventData = DataManager.getPlayerData(player).getEventData();
		if (eventData != null) {
			if (!eventData.getEvent().canPlace()) {
				event.setCancelled(true);
			}
		}
	}
}
