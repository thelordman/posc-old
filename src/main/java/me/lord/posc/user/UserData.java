package me.lord.posc.user;

import java.io.Serializable;
import java.util.UUID;

/**
 * A user's data which won't be wiped on a server reload
 * or a server restart.
 */
final class UserData {
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
