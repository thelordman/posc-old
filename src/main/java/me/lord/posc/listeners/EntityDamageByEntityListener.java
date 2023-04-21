package me.lord.posc.listeners;

import me.lord.posc.data.DataManager;
import me.lord.posc.event.Event;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity victim = event.getEntity();
        Entity attacker = event.getDamager();

        if (attacker instanceof Player) {
            Event.Data eventData = DataManager.getPlayerData((Player) attacker).getEventData();
            if (!eventData.getEvent().canHit()) {
                event.setCancelled(true);
            }
        }
    }
}
