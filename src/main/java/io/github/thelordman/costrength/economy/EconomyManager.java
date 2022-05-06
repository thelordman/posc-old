package io.github.thelordman.costrength.economy;

import io.github.thelordman.costrength.utilities.Data;

import java.util.UUID;

public final class EconomyManager {
    static Data data = new Data();

    public static Float getBalance(UUID uuid) {
        return data.balance.getOrDefault(uuid, 0f);
    }

    public static void setBalance(UUID uuid, Float amount) {
        data.balance.put(uuid, amount);
    }

    public static Float getXp(UUID uuid) {
        return data.xp.getOrDefault(uuid, 0f);
    }

    public static void setXp(UUID uuid, Float amount) {
        data.xp.put(uuid, amount);
    }

    public static Integer getLevel(UUID uuid) {
        if (!data.level.containsKey(uuid)) return 1;
        if (data.level.get(uuid) == 0) return 1;
        return data.level.get(uuid);
    }

    public static void setLevel(UUID uuid, Integer amount) {
        data.level.put(uuid, amount);
    }

    public static Integer getKillstreak(UUID uuid) {
        return data.killstreak.getOrDefault(uuid, 0);
    }

    public static void setKillstreak(UUID uuid, Integer amount) {
        data.killstreak.put(uuid, amount);
    }

    public static Float getBounty(UUID uuid) {
        return data.bounty.getOrDefault(uuid, 0f);
    }

    public static void setBounty(UUID uuid, Float amount) {
        data.bounty.put(uuid, amount);
    }
}