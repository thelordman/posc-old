package io.github.thelordman.costrength.economy;

import io.github.thelordman.costrength.utilities.Data;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;

import java.util.UUID;

public final class EconomyManager {
    static Data data = new Data();

    public static Double getBalance(UUID uuid) {
        if (Bukkit.getOfflinePlayer(uuid).isOnline()) return data.balance.get(uuid);
        else return (double) Bukkit.getOfflinePlayer(uuid).getStatistic(Statistic.USE_ITEM, Material.GOLD_NUGGET);
    }

    public static void setBalance(UUID uuid, Double amount) {
        if (Bukkit.getOfflinePlayer(uuid).isOnline()) data.balance.put(uuid, amount);
        else Bukkit.getOfflinePlayer(uuid).setStatistic(Statistic.USE_ITEM, Material.GOLD_NUGGET, amount.intValue());
    }

    public static Double getXp(UUID uuid) {
        if (Bukkit.getOfflinePlayer(uuid).isOnline()) return data.xp.get(uuid);
        else return (double) Bukkit.getOfflinePlayer(uuid).getStatistic(Statistic.USE_ITEM, Material.EXPERIENCE_BOTTLE);
    }

    public static void setXp(UUID uuid, Double amount) {
        if (Bukkit.getOfflinePlayer(uuid).isOnline()) data.xp.put(uuid, amount);
        else Bukkit.getOfflinePlayer(uuid).setStatistic(Statistic.USE_ITEM, Material.EXPERIENCE_BOTTLE, amount.intValue());
    }

    public static Integer getLevel(UUID uuid) {
        if (Bukkit.getOfflinePlayer(uuid).isOnline()) return data.level.getOrDefault(uuid, 1);
        return Bukkit.getOfflinePlayer(uuid).getStatistic(Statistic.USE_ITEM, Material.FIREWORK_ROCKET) == 0 ? 1 : Bukkit.getOfflinePlayer(uuid).getStatistic(Statistic.USE_ITEM, Material.FIREWORK_ROCKET);
    }

    public static void setLevel(UUID uuid, Integer amount) {
        if (Bukkit.getOfflinePlayer(uuid).isOnline()) data.level.put(uuid, amount);
        else Bukkit.getOfflinePlayer(uuid).setStatistic(Statistic.USE_ITEM, Material.FIREWORK_ROCKET, amount);
    }

    public static Integer getKillstreak(UUID uuid) {
        if (Bukkit.getOfflinePlayer(uuid).isOnline()) return data.killstreak.get(uuid);
        else return Bukkit.getOfflinePlayer(uuid).getStatistic(Statistic.USE_ITEM, Material.WOODEN_SWORD);
    }

    public static void setKillstreak(UUID uuid, Integer amount) {
        if (Bukkit.getOfflinePlayer(uuid).isOnline()) data.killstreak.put(uuid, amount);
        else Bukkit.getOfflinePlayer(uuid).setStatistic(Statistic.USE_ITEM, Material.WOODEN_SWORD, amount);
    }

    public static Double getBounty(UUID uuid) {
        if (Bukkit.getOfflinePlayer(uuid).isOnline()) return data.bounty.get(uuid);
        else return (double) Bukkit.getOfflinePlayer(uuid).getStatistic(Statistic.USE_ITEM, Material.SPYGLASS);
    }

    public static void setBounty(UUID uuid, Double amount) {
        if (Bukkit.getOfflinePlayer(uuid).isOnline()) data.bounty.put(uuid, amount);
        else Bukkit.getOfflinePlayer(uuid).setStatistic(Statistic.USE_ITEM, Material.SPYGLASS, amount.intValue());
    }
}