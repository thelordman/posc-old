package me.lord.posc.listeners;

import me.lord.posc.data.DataManager;
import me.lord.posc.event.Event;
import me.lord.posc.event.ParkourEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack item = event.getItem();

		Event.Data eventData = DataManager.getPlayerData(player).getEventData();
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
