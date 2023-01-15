package me.lord.posc.data;

import me.lord.posc.Posc;

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

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public void incrementTotalUsers() {
        totalUsers++;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        try {
            out.writeInt(totalUsers);
        } catch (IOException e) {
            String field = "totalUsers";
            Posc.LOGGER.warning("Could not write + '" + field + "', writing default value");
            out.writeInt(0);
        }
    }

    @Override
    public void readExternal(ObjectInput in) {
        try {
            setTotalUsers(in.readInt());
        } catch (IOException e) {
            String field = "totalUsers";
            Posc.LOGGER.warning("Could not read + '" + field + "', setting to default value");
            setTotalUsers(0);
        }
    }
}
