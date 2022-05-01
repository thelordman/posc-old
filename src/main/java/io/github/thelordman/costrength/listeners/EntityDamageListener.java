package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Player victim = (Player) event.getEntity();
        Player attacker = (Player) event.getEntity().getLastDamageCause().getEntity();

        if (event.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION)) {
            event.setCancelled(true);
            return;
        }
        if (attacker == null | victim == null) return;

        if (Methods.inSpawn(attacker) && Methods.inSpawn(victim)) {
            attacker.sendMessage("ur in spawn and ur attacking");
            victim.sendMessage("ur in spawn and ur the victim");
        }
    }
}