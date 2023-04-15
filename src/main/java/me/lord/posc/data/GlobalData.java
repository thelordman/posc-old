package me.lord.posc.data;

import me.lord.posc.economy.Market;

import java.io.*;

/**
 * Stores data that is global (not attached to an entity, item or a block).
 */
public final class GlobalData implements Data {
    @Serial
    private static final long serialVersionUID = 1532434026207612324L;

    private int totalUsers = 0;
    private Market market = new Market();

    private transient Integer consoleSelectedNPC = null;

    public int getTotalUsers() {
        return totalUsers;
    }

    public void incrementTotalUsers() {
        totalUsers++;
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
}
