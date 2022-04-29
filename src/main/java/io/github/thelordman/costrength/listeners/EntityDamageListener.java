package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Player victim = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;
        Player attacker = event.getEntity().getLastDamageCause() instanceof Player ? (Player) event.getEntity().getLastDamageCause() : null;

        if (victim == null) return;

        if (Methods.inSpawn(victim) | Methods.inSpawn(attacker)) {

        }
    }
}