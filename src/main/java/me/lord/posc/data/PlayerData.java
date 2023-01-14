package me.lord.posc.data;

import java.util.UUID;

/**
 * A player's data which won't be wiped on a server reload or a server restart.
 */
public final class PlayerData extends Data {
    private final UUID uuid;

    private double balance;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        balance = 0d;
    }

    public UUID getUUID() {
        return uuid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
