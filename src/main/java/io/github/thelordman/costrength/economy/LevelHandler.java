package io.github.thelordman.costrength.economy;

import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

public class LevelHandler {
    public static Float xpRequirement(Player player) {
        int l = EconomyManager.getLevel(player);
        return (float) l * 1000;
    }
    public static void xp(Player player) {
        if (EconomyManager.getXp(player) >= xpRequirement(player)) {
            EconomyManager.setLevel(player, EconomyManager.getLevel(player) + 1);
            EconomyManager.setXp(player, 0f);

            if (List.of(10, 25, 50, 75, 100, 150, 200, 300, 400, 500, 600, 700, 800, 900, 1000).contains(EconomyManager.getLevel(player))) {
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                Bukkit.broadcastMessage(Methods.cStr(player.getDisplayName() + " &6has reached level &f" + EconomyManager.getLevel(player)));
            }

            player.setLevel(EconomyManager.getLevel(player));
            player.setExp(0);
            Methods.updateDisplayName(player);
        }
        else {
            player.setExp(EconomyManager.getXp(player) / xpRequirement(player));
        }
    }
}