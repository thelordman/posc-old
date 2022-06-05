package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.items.ItemManager;
import io.github.thelordman.costrength.utilities.Data;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        Player victim = (Player) event.getEntity();
        Player attacker = (Player) event.getDamager();
        ItemStack item = attacker.getActiveItem();

        if (Methods.inSpawn(victim) | Methods.inSpawn(attacker)) {
            if (!Methods.inCombat(victim)) {
                event.setCancelled(true);
                return;
            }
        }

        if (ItemManager.getCELevel(item, ItemManager.swordEnchantments[0]) != 0) {
            if (Math.random() < ((double) ItemManager.getCELevel(item, ItemManager.swordEnchantments[0])) / 50) {
                victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10000, 0));
            }
        }

        Data.combatTag.put(attacker, System.currentTimeMillis());
        Data.combatTag.put(victim, System.currentTimeMillis());
    }
}