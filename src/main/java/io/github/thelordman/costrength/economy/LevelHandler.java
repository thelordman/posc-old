package io.github.thelordman.costrength.economy;

import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class LevelHandler {
    public static Float xpRequirement(Player player) {
        int l = EconomyManager.getLevel(player);
        return (float) l * 1000;
    }
    public static void xp(Player player) {
        if (EconomyManager.getXp(player) >= xpRequirement(player)) {
            player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
            EconomyManager.setLevel(player, EconomyManager.getLevel(player) + 1);
            EconomyManager.setXp(player, 0f);
            player.setLevel(EconomyManager.getLevel(player));
            player.setExp(0);
            Methods.updateDisplayName(player);
        }
        else {
            player.playEffect(player.getLocation(), Effect.GRINDSTONE_USE, player);
            player.setExp(EconomyManager.getXp(player) / xpRequirement(player));
        }
    }
}