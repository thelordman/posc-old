package io.github.thelordman.costrength.economy;

import io.github.thelordman.costrength.utilities.data.DataManager;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

import java.util.UUID;

public final class EconomyManager {

    public static double getBalance(UUID uuid) {
        return DataManager.getPlayerData(uuid).getBalance();
    }

    public static void setBalance(UUID uuid, double amount) {
        DataManager.getPlayerData(uuid).setBalance(amount);
    }

    public static double getXp(UUID uuid) {
        return DataManager.getPlayerData(uuid).getXp();
    }

    public static void setXp(UUID uuid, double amount) {
        DataManager.getPlayerData(uuid).setXp(amount);
    }

    public static double getBlocks(OfflinePlayer player) {
        return (double) player.getStatistic(Statistic.USE_ITEM, Material.IRON_PICKAXE) + player.getStatistic(Statistic.USE_ITEM, Material.DIAMOND_PICKAXE) + player.getStatistic(Statistic.USE_ITEM, Material.NETHERITE_PICKAXE) + player.getStatistic(Statistic.USE_ITEM, Material.GOLDEN_PICKAXE);
    }

    public static int getLevel(UUID uuid) {
        return DataManager.getPlayerData(uuid).getLevel();
    }

    public static void setLevel(UUID uuid, int amount) {
        DataManager.getPlayerData(uuid).setLevel(amount);
    }

    public static double getKills(OfflinePlayer player) {
        return player.getStatistic(Statistic.PLAYER_KILLS);
    }

    public static double getDeaths(OfflinePlayer player) {
        return player.getStatistic(Statistic.DEATHS);
    }

    public static int getKillstreak(UUID uuid) {
        return DataManager.getPlayerData(uuid).getKillStreak();
    }

    public static void setKillstreak(UUID uuid, int amount) {
        DataManager.getPlayerData(uuid).setKillStreak(amount);
    }

    public static double getBounty(UUID uuid) {
        return DataManager.getPlayerData(uuid).getBounty();
    }

    public static void setBounty(UUID uuid, double amount) {
        DataManager.getPlayerData(uuid).setBounty(amount);
    }
}