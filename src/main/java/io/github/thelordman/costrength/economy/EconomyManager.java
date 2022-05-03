package io.github.thelordman.costrength.economy;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public final class EconomyManager {
    public static final HashMap<UUID, Float> balance = new HashMap<>();
    public static final HashMap<UUID, Float> xp = new HashMap<>();
    public static final HashMap<UUID, Integer> level = new HashMap<>();
    public static final HashMap<UUID, Integer> killstreak = new HashMap<>();

    public static Float getBalance(Player player) {
        return balance.get(player.getUniqueId()) == null ? 0f : balance.get(player.getUniqueId());
    }

    public static void setBalance(Player player, Float amount) {
        balance.put(player.getUniqueId(), amount);
    }

    public static Float getXp(Player player) {
        return xp.get(player.getUniqueId()) == null ? 0f : xp.get(player.getUniqueId());
    }

    public static void setXp(Player player, Float amount) {
        xp.put(player.getUniqueId(), amount);
    }

    public static Integer getLevel(Player player) {
        return level.get(player.getUniqueId()) == null ? 1 : level.get(player.getUniqueId());
    }

    public static void setLevel(Player player, Integer amount) {
        level.put(player.getUniqueId(), amount);
    }

    public static Integer getKillstreak(Player player) {
        return killstreak.get(player.getUniqueId()) == null ? 0 : killstreak.get(player.getUniqueId());
    }

    public static void setKillstreak(Player player, Integer amount) {
        killstreak.put(player.getUniqueId(), amount);
    }
}