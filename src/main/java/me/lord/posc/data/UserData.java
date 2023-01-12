package me.lord.posc.data;

import java.util.UUID;

/**
 * A user's data which won't be wiped on a server reload or a server restart.
 */
public final class UserData {
    private final UUID uuid;

    private double balance;

    UserData(UUID uuid) {
        this.uuid = uuid;
        balance = 0d;
    }

    UUID getUUID() {
        return uuid;
    }

    double getBalance() {
        return balance;
    }

    void setBalance(double balance) {
        this.balance = balance;
    }
}
