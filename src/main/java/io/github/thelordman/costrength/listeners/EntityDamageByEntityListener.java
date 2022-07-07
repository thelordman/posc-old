package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.items.ItemManager;
import io.github.thelordman.costrength.utilities.Methods;
import io.github.thelordman.costrength.utilities.data.Data;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.javatuples.Pair;

public class EntityDamageByEntityListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.isCancelled() | event.getCause() == EntityDamageEvent.DamageCause.THORNS) return;
        Player victim = (Player) event.getEntity();
        Player attacker = (Player) event.getDamager();
        ItemStack attackerItem = attacker.getInventory().getItemInMainHand();
        ItemStack victimItem = victim.getInventory().getItemInMainHand();

        if (Methods.inSpawn(victim.getLocation()) | Methods.inSpawn(attacker.getLocation())) {
            if (!Methods.inCombat(victim)) {
                event.setCancelled(true);
                return;
            }
        }

        if (ItemManager.getCELevel(attackerItem, ItemManager.swordEnchantments[0]) != 0) {
            if (Math.random() < ((double) ItemManager.getCELevel(attackerItem, ItemManager.swordEnchantments[0])) / 50) {
                victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 200, 0));
            }
        }
        if (ItemManager.getCELevel(victimItem, ItemManager.swordEnchantments[2]) != 0) {
            if (Math.random() < ((double) ItemManager.getCELevel(victimItem, ItemManager.swordEnchantments[0])) / 20) {
                event.setDamage(event.getFinalDamage() - event.getFinalDamage() / 3);
            }
        }
        if (ItemManager.getCELevel(attackerItem, ItemManager.swordEnchantments[3]) != 0) {
            if (Math.random() < ((double) ItemManager.getCELevel(attackerItem, ItemManager.swordEnchantments[0])) / 50) {
                victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 0));
            }
        }
        if (ItemManager.getCELevel(attackerItem, ItemManager.swordEnchantments[4]) != 0) {
            attacker.setFoodLevel(attacker.getFoodLevel() + ItemManager.getCELevel(attackerItem, ItemManager.swordEnchantments[4]));
            attacker.setSaturation(attacker.getSaturation() + ItemManager.getCELevel(attackerItem, ItemManager.swordEnchantments[4]));
        }
        if (ItemManager.getCELevel(attackerItem, ItemManager.swordEnchantments[5]) != 0) {
            byte amount = 1;
            if (Data.lastHitData.containsKey(attacker)) {
                if (Data.lastHitData.get(attacker).getValue0() == victim) {
                    amount = (byte) (Data.lastHitData.get(attacker).getValue1() == 126 ? 126 : Data.lastHitData.get(attacker).getValue1() + 1);
                }
            }
            Data.lastHitData.put(attacker, Pair.with(victim, amount));

            amount--;
            byte level = ItemManager.getCELevel(attackerItem, ItemManager.swordEnchantments[5]);
            event.setDamage(event.getFinalDamage() + event.getFinalDamage() / 100 * amount * level);
        }
        if (ItemManager.getCELevel(attackerItem, ItemManager.swordEnchantments[6]) != 0) {
            if (Math.random() < ((double) ItemManager.getCELevel(attackerItem, ItemManager.swordEnchantments[6])) / 20) {
                double amount = ItemManager.getCELevel(attackerItem, ItemManager.swordEnchantments[6]) * 0.5;
                victim.setHealth(Math.max(victim.getHealth() - amount, 0d));
                attacker.setHealth(Math.min(attacker.getHealth() + amount, attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
            }
        }
        if (ItemManager.getCELevel(attackerItem, ItemManager.swordEnchantments[7]) != 0) {
            if (Math.random() < ((double) ItemManager.getCELevel(attackerItem, ItemManager.swordEnchantments[7])) / 50) {
                attacker.removePotionEffect(PotionEffectType.POISON);
                attacker.removePotionEffect(PotionEffectType.BLINDNESS);
                attacker.setFireTicks(0);
            }
        }

        Data.combatTag.put(attacker, System.currentTimeMillis());
        Data.combatTag.put(victim, System.currentTimeMillis());
    }
}