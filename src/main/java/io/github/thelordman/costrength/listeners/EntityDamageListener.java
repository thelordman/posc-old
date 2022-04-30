package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.utilities.Data;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Player victim = Bukkit.getPlayer(event.getEntity().getName());
        Player attacker = Bukkit.getPlayer(event.getEntity().getLastDamageCause().getEntity().getName());

        if (event.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION)) {
            event.setCancelled(true);
            return;
        }

        if (attacker != null && victim != null) {
            if (Methods.inSpawn(attacker) | Methods.inSpawn(victim)) {
                if (Data.combatTag.containsKey(victim)) {
                    Methods.addCombatTag(victim);
                    return;
                }
                event.setCancelled(true);
                return;
            }
            if (!Data.combatTag.containsKey(victim)) victim.sendMessage(Methods.cStr("&cCombat tagged &6with " + attacker + " &6for &f20 seconds&6."));
            Methods.addCombatTag(victim);
            if (!Data.combatTag.containsKey(attacker)) attacker.sendMessage(Methods.cStr("&cCombat tagged &6with " + victim + " &6for &f20 seconds&6."));
            Methods.addCombatTag(victim);
        }
    }
}