package io.github.thelordman.costrength.economy;

import org.bukkit.entity.Player;

public class LevelHandler {
    public static Float xpRequirement(Player player) {
        int l = EconomyManager.getLevel(player);
        return (float) l * 1000;
    }
    public static void levelUp(Player player) {

    }
}