package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (!event.isCancelled() && event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player[] players = {((Player) event.getDamager()),((Player) event.getEntity())};
            if (Methods.inSpawn((Player) event.getDamager()) | Methods.inSpawn((Player) event.getEntity()) && !Methods.inCombat((Player) event.getEntity())) event.setCancelled(true);
            else{
                for(Player player : players) {
                    if (Methods.inCombat(player)) Methods.setCombatTicks(player, 20 * 21);
                    else {
                        Methods.addPlayer(player, 20 * 20);
                    }
                }
            }
        }
        else if(event.getEntity() instanceof ArmorStand && event.getDamager() instanceof Player){
            event.setCancelled(true);
        }
    }
}
