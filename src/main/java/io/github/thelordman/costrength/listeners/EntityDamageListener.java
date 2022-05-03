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

        if (!event.isCancelled() && attacker != null && victim != null) {
            Player[] players = {attacker,victim};
            if (!Methods.inCombat(victim)) victim.sendMessage(Methods.cStr("&cYou are in combat with &6" + attacker.getName() + " &cfor 20 seconds!"));
            if (!Methods.inCombat(attacker)) attacker.sendMessage(Methods.cStr("&cYou are in combat with &6" + victim.getName() + " &cfor 20 seconds!"));
            if (Methods.inSpawn(attacker) | Methods.inSpawn(victim)) {
                if (Methods.inCombat(victim)) {
                    Data.combatTag.put(victim, (byte) 20);
                    Data.combatTag.put(attacker, (byte) 20);
                    return;
                }
                event.setCancelled(true);
            }
            else {
                for (Player player : players) {
                    if (Methods.inCombat(player)) Data.combatTag.put(player, (byte) 20);
                    else Data.combatTag.put(victim, (byte) 20);
                }
            }
        }
    }
}