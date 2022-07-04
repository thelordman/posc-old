package io.github.thelordman.costrength.economy;

import io.github.thelordman.costrength.utilities.data.PlayerDataManager;

import java.util.UUID;

public final class EconomyManager {

    public static Double getBalance(UUID uuid) {
        return PlayerDataManager.getPlayerData(uuid).getBalance();
    }

    public static void setBalance(UUID uuid, Double amount) {
        PlayerDataManager.getPlayerData(uuid).setBalance(amount);
    }

    public static Double getXp(UUID uuid) {
        return PlayerDataManager.getPlayerData(uuid).getXp();
    }

    public static void setXp(UUID uuid, Double amount) {
        PlayerDataManager.getPlayerData(uuid).setXp(amount);
    }

    public static Integer getLevel(UUID uuid) {
        return PlayerDataManager.getPlayerData(uuid).getLevel();
    }

    public static void setLevel(UUID uuid, Integer amount) {
        PlayerDataManager.getPlayerData(uuid).setLevel(amount);
    }

    public static Integer getKillstreak(UUID uuid) {
        return PlayerDataManager.getPlayerData(uuid).getKillStreak();
    }

    public static void setKillstreak(UUID uuid, Integer amount) {
        PlayerDataManager.getPlayerData(uuid).setKillStreak(amount);
    }

    public static Double getBounty(UUID uuid) {
        return PlayerDataManager.getPlayerData(uuid).getBounty();
    }

    public static void setBounty(UUID uuid, Double amount) {
        PlayerDataManager.getPlayerData(uuid).setBounty(amount);
    }
}