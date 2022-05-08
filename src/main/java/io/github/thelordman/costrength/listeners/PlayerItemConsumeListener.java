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

        if (item.getType().equals(Material.RABBIT_STEW)) {
            player.setSaturation(20);
            player.setFoodLevel(20);
        }
        if (item.getType().equals(Material.COOKED_CHICKEN) && Math.random() <= 0.1) {
            int h = player.getPotionEffect(PotionEffectType.FAST_DIGGING) == null ? 0
                    : player.getPotionEffect(PotionEffectType.FAST_DIGGING).getAmplifier() + 1;
            int t = player.getPotionEffect(PotionEffectType.FAST_DIGGING) == null ? 1200
                    : player.getPotionEffect(PotionEffectType.FAST_DIGGING).getDuration() + 1200;
            if (h == 4) h = 3;
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, t, h));
            player.sendMessage(Methods.cStr("&6The &cHot Wings &6you ate made you faster at mining.\n&fHaste " + h + " for " + Methods.rStr(((float) t) / 1200f) + " minutes &7(+1 haste)&f."));
            return;
        }
        if (item.getLore().contains(Methods.cStr("&2Melon&7: &6Restores &f+2 hunger &6after drinking."))) {
            player.setFoodLevel(player.getFoodLevel() + 4);
        }
        if (item.getLore().contains(Methods.cStr("&cBerries&7: &6Restores &f+7 saturation &6after drinking."))) {
            player.setSaturation(player.getSaturation() + 14);
        }
        if (item.getLore().contains(Methods.cStr("&fSugar&7: &6Gives &fspeed 1 for 20 seconds &6after drinking."))) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 400, 0));
        }
        if (item.getLore().contains(Methods.cStr("&eAlcohol&7: &6Gives &fstrength 1 for 20 seconds &6after drinking."))) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 400, 0));
        }
    }
}