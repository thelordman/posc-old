package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerItemConsumeListener implements Listener {
    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item.getType().equals(Material.SWEET_BERRIES) | item.getType().equals(Material.MELON) | item.getType().equals(Material.POTION)) event.setCancelled(true);

        if (item.getType().equals(Material.COOKED_CHICKEN) && Math.random() <= 0.1) {
            int h = player.getPotionEffect(PotionEffectType.FAST_DIGGING) == null ? 0
                    : player.getPotionEffect(PotionEffectType.FAST_DIGGING).getAmplifier() + 1;
            int t = player.getPotionEffect(PotionEffectType.FAST_DIGGING) == null ? 1200
                    : player.getPotionEffect(PotionEffectType.FAST_DIGGING).getDuration() + 1200;
            if (h == 4) h = 3;
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, t, h));
            player.sendMessage(Methods.cStr("&6The &cHot Wings &6you ate made you faster at mining.\n&fHaste " + h + " for " + Methods.rStr(((float) t) / 1200f) + " minutes &7(+1 haste)&f."));
        }
    }
}