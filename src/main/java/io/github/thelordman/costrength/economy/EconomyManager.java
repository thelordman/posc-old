package io.github.thelordman.costrength.economy;

import io.github.thelordman.costrength.utilities.data.PlayerDataManager;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

import java.util.UUID;

public final class EconomyManager {

    public static double getBalance(UUID uuid) {
        return PlayerDataManager.getPlayerData(uuid).getBalance();
    }

    public static void setBalance(UUID uuid, double amount) {
        PlayerDataManager.getPlayerData(uuid).setBalance(amount);
    }

    public static double getXp(UUID uuid) {
        return PlayerDataManager.getPlayerData(uuid).getXp();
    }

    public static void setXp(UUID uuid, double amount) {
        PlayerDataManager.getPlayerData(uuid).setXp(amount);
    }

    public static double getBlocks(OfflinePlayer player) {
        return (double) player.getStatistic(Statistic.USE_ITEM, Material.IRON_PICKAXE) + player.getStatistic(Statistic.USE_ITEM, Material.DIAMOND_PICKAXE) + player.getStatistic(Statistic.USE_ITEM, Material.NETHERITE_PICKAXE) + player.getStatistic(Statistic.USE_ITEM, Material.GOLDEN_PICKAXE);
    }

    public static int getLevel(UUID uuid) {
        return PlayerDataManager.getPlayerData(uuid).getLevel();
    }

    public static void setLevel(UUID uuid, int amount) {
        PlayerDataManager.getPlayerData(uuid).setLevel(amount);
    }

    public static double getKills(OfflinePlayer player) {
        return player.getStatistic(Statistic.PLAYER_KILLS);
    }

    public static double getDeaths(OfflinePlayer player) {
        return player.getStatistic(Statistic.DEATHS);
    }

    public static int getKillstreak(UUID uuid) {
        return PlayerDataManager.getPlayerData(uuid).getKillStreak();
    }

    public static void setKillstreak(UUID uuid, int amount) {
        PlayerDataManager.getPlayerData(uuid).setKillStreak(amount);
    }

    public static double getBounty(UUID uuid) {
        return PlayerDataManager.getPlayerData(uuid).getBounty();
    }

    public static void setBounty(UUID uuid, double amount) {
        PlayerDataManager.getPlayerData(uuid).setBounty(amount);
    }
}