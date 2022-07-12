package io.github.thelordman.costrength.utilities.data;

import io.github.thelordman.costrength.punishments.Punishment;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerData implements Serializable {
    @Serial
    private static final long serialVersionUID = 3022193081975011242L;

    private final UUID uuid;

    private double balance = 0d;
    private double bounty = 0d;
    private double xp = 0d;
    private int level = 1;
    private int killStreak = 0;
    private boolean staffMode = false;

    private Rank rank = Rank.DEFAULT;
    private final ArrayList<Punishment> punishments = new ArrayList<>();

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
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

    public double getBounty() {
        return bounty;
    }

    public void setBounty(double bounty) {
        this.bounty = bounty;
    }

    public double getXp() {
        return xp;
    }

    public void setXp(double xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getKillStreak() {
        return killStreak;
    }

    public void setKillStreak(int killStreak) {
        this.killStreak = killStreak;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public boolean inStaffMode() {
        return staffMode;
    }

    public void setStaffMode(boolean staffMode) {
        this.staffMode = staffMode;
    }

    public ArrayList<Punishment> getPunishments() {
        return punishments;
    }

    public void addPunishment(Punishment punishment) {
        this.punishments.add(punishment);
    }

    public void removePunishment(int ID) {
        this.punishments.forEach((p) -> {
            if (p.getID() == ID) this.punishments.remove(p);
        });
    }
}