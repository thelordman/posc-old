package io.github.thelordman.costrength.listeners;


import io.github.thelordman.costrength.utilities.Data;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Player victim = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;
        Player attacker = event.getEntity().getLastDamageCause() instanceof Player ? (Player) event.getEntity().getLastDamageCause() : null;
        BossBar combatTagBar = Bukkit.createBossBar(Methods.cStr("&cCombat Tag"), BarColor.RED, BarStyle.SEGMENTED_20);

        if (event.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION)) {
            event.setCancelled(true);
            return;
        }

        if (attacker != null && victim != null) {
            if (Methods.inSpawn(attacker) | Methods.inSpawn(victim)) {
                if (Data.combatTag.get(victim) != null) {
                    Data.combatTag.put(victim, (byte) 20);
                    combatTagBar.addPlayer(victim);
                    return;
                }
                event.setCancelled(true);
                return;
            }

            if (Data.combatTag.get(victim) == null) victim.sendMessage(Methods.cStr("&cCombat tagged &6with " + attacker + " &6for &f20 seconds&6."));
            if (Data.combatTag.get(attacker) == null) attacker.sendMessage(Methods.cStr("&cCombat tagged &6with " + victim + " &6for &f20 seconds&6."));

        }
    }
}