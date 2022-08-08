package io.github.thelordman.posc.listeners;

import io.github.thelordman.posc.items.ItemManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerItemHeldListener implements Listener {
    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        ItemStack newItem = event.getPlayer().getInventory().getItem(event.getNewSlot());
        if (ItemManager.getCELevel(newItem, ItemManager.pickaxeEnchantments[3]) != 0) {
            event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, ItemManager.getCELevel(newItem, ItemManager.pickaxeEnchantments[3]) - 1));
        }
        else {
            event.getPlayer().removePotionEffect(PotionEffectType.FAST_DIGGING);
        }
    }
}