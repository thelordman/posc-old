package me.lord.posc.economy.offer;

import me.lord.posc.economy.Market;
import me.lord.posc.economy.MarketEntry;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public abstract class Offer implements Serializable {
    protected final transient Material type;
    protected int amount;
    protected final int initialAmount;
    protected final double value;
    protected final UUID player;
    protected final long date;

    protected Offer(Material type, int amount, double value, UUID player) {
        this.type = type;
        this.initialAmount = amount;
        this.amount = amount;
        this.value = value;
        this.player = player;
        date = Instant.now().getEpochSecond();
    }

    public abstract void fill(Player player, int amount);

    public abstract void claim();

    public boolean isComplete() {
        return amount == 0;
    }

    public int getProcessed() {
        return initialAmount - amount;
    }

    public MarketEntry getEntry() {
        return Market.getCurrent().getEntry(type);
    }

    public Material getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public double getValue() {
        return value;
    }

    public UUID getPlayer() {
        return player;
    }

    public long getDate() {
        return date;
    }
}
