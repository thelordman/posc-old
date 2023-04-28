package me.lord.posc.listeners;

import me.lord.posc.data.DataManager;
import me.lord.posc.event.Event;
import me.lord.posc.event.ParkourEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		ItemStack item = event.getCurrentItem();
		if (event.getWhoClicked() instanceof Player player) {
			Event.Data eventData = DataManager.getPlayerData(player).getEventData();
			if (eventData != null) {
				if (!eventData.getEvent().canMoveItems()) {
					event.setCancelled(true);
				}
				if (eventData instanceof ParkourEvent.Data parkourData) {
					if (item != null) {
						switch (item.getType()) {
							case GOLD_BLOCK -> parkourData.teleportCheckpoint();
							case MAGENTA_GLAZED_TERRACOTTA -> ParkourEvent.Checkpoint.START.teleport(player);
							case BARRIER -> parkourData.getEvent().playerLeave(player);
						}
					}
				}
			}
		}
	}
}
