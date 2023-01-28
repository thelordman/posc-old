package me.lord.posc.data;

import java.io.*;

/**
 * Stores data that is global (not attached to an entity, item or a block).
 */
public final class GlobalData implements Data {
    @Serial
    private static final long serialVersionUID = 1532434026207612324L;

    private int totalUsers = 0;

    public int getTotalUsers() {
        return totalUsers;
    }

    public void incrementTotalUsers() {
        totalUsers++;
    }

}
