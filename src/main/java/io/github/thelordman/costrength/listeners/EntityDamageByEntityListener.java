package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.utilities.Data;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        Player victim = (Player) event.getEntity();
        Player attacker = (Player) event.getEntity().getLastDamageCause().getEntity();
        if (event.isCancelled()) return;

        if (event.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION)) {
            event.setCancelled(true);
            return;
        }

        Player[] players = {attacker, victim};
        if (!Methods.inCombat(victim)) victim.sendMessage(Methods.cStr("&cYou are in combat with &6" + attacker.getName() + " &cfor 20 seconds!"));
        if (!Methods.inCombat(attacker)) attacker.sendMessage(Methods.cStr("&cYou are in combat with &6" + victim.getName() + " &cfor 20 seconds!"));
        if (Methods.inSpawn(attacker) | Methods.inSpawn(victim)) {
            if (Methods.inCombat(victim)) {
                Data.combatTag.put(victim, (byte) 20);
                return;
            }
            event.setCancelled(true);
        }
        else {
            for (Player player : players) {
                if (Methods.inCombat(player)) Data.combatTag.put(player, (byte) 20);
                else Methods.addPlayer(player, (byte) 20);
            }
        }
    }
}