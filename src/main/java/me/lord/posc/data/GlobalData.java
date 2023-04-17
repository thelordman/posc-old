package me.lord.posc.data;

import me.lord.posc.economy.Market;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.ArrayList;

/**
 * Stores data that is global (not attached to an entity, item or a block).
 */
public final class GlobalData implements Data {
    @Serial
    private static final long serialVersionUID = 1532434026207612324L;

    private Market market = new Market();
    private final ArrayList<String> worlds = new ArrayList<>();

    private transient int totalUsers = Bukkit.getOfflinePlayers().length;
    private transient Integer consoleSelectedNPC = null;

    public int getTotalUsers() {
        return totalUsers;
    }

    public void incrementTotalUsers() {
        totalUsers++;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public Integer getConsoleSelectedNPC() {
        return consoleSelectedNPC;
    }

    public void setConsoleSelectedNPC(Integer selectedNPC) {
        consoleSelectedNPC = selectedNPC;
    }

    public ArrayList<String> getWorlds() {
        return worlds;
    }
}
