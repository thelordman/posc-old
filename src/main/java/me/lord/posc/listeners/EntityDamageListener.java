package me.lord.posc.listeners;

import me.lord.posc.data.DataManager;
import me.lord.posc.data.PlayerData;
import me.lord.posc.event.Event;
import me.lord.posc.event.ParkourEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();

		if (entity instanceof Player player) {
			PlayerData data = DataManager.getPlayerData(player);
			if (data.godMode()) {
				event.setCancelled(true);
			}

			Event.Data eventData = data.getEventData();
			if (eventData instanceof ParkourEvent.Data parkourData && event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
				parkourData.teleportCheckpoint();
			}
		}
	}
}
