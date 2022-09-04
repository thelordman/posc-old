package io.github.thelordman.posc.food;

import io.github.thelordman.posc.utilities.Methods;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FoodManager {
    public static void registerFoodUtil() {
        FoodUtil.register();
    }

    public static boolean consume(Player player, ItemStack item) {
        if (item.getType().equals(Material.SWEET_BERRIES) | item.getType().equals(Material.MELON) | item.getType().equals(Material.POTION)) return false;

        if (item.getType().equals(Material.COOKED_CHICKEN) && Math.random() <= 0.1) {
            int h = player.getPotionEffect(PotionEffectType.FAST_DIGGING) == null ? 0
                    : player.getPotionEffect(PotionEffectType.FAST_DIGGING).getAmplifier() + 1;
            int t = player.getPotionEffect(PotionEffectType.FAST_DIGGING) == null ? 1200
                    : player.getPotionEffect(PotionEffectType.FAST_DIGGING).getDuration() + 1200;
            if (h == 4) h = 3;
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, t, h));
            player.sendMessage(Methods.cStr("&6The &cHot Wings &6you ate made you faster at mining.\n&fHaste " + h + " for " + Methods.rStr(((double) t) / 1200) + " minutes &7(+1 haste)&f."));
            return true;
        }

        if (FoodUtil.getHunger(item) != 0 | FoodUtil.getSaturation(item) != 0) {
            player.setFoodLevel(player.getFoodLevel() - FoodUtil.getVanillaHunger(item) + FoodUtil.getHunger(item));
            player.setSaturation(player.getSaturation() - FoodUtil.getVanillaSaturation(item) + FoodUtil.getSaturation(item));
        }

        return true;
    }
}
