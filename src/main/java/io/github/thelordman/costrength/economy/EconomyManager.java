package io.github.thelordman.costrength.economy;

import io.github.thelordman.costrength.utilities.Data;

import java.util.UUID;

public final class EconomyManager {
    static Data data = new Data();

    public static Float getBalance(UUID uuid) {
        return data.balance.containsKey(uuid) ? 0f : data.balance.get(uuid);
    }

    public static void setBalance(UUID uuid, Float amount) {
        data.balance.put(uuid, amount);
    }

    public static Float getXp(UUID uuid) {
        return data.xp.containsKey(uuid) ? 0f : data.xp.get(uuid);
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
        return data.killstreak.containsKey(uuid) ? 0 : data.killstreak.get(uuid);
    }

    public static void setKillstreak(UUID uuid, Integer amount) {
        data.killstreak.put(uuid, amount);
    }

    public static Float getBounty(UUID uuid) {
        return data.bounty.containsKey(uuid) ? 0f : data.bounty.get(uuid);
    }

    public static void setBounty(UUID uuid, Float amount) {
        data.bounty.put(uuid, amount);
    }
}