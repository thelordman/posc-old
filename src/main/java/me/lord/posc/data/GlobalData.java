package me.lord.posc.data;

/**
 * Stores data that is global (not attached to an entity, item or a block).
 */
public final class GlobalData extends Data {
    private int totalUsers = 0;

    public int getTotalUsers() {
        return totalUsers;
    }

    public void incrementTotalUsers() {
        totalUsers++;
    }
}
