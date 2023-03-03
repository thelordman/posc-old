package me.lord.posc.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.Serial;

/**
 * An NPC's data which won't be wiped on a server reload or a server restart.
 */
public class NPCData implements Data {
    @Serial
    private static final long serialVersionUID = 8226916606328208331L;

    // Location
    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;

    private final int index;
    private final String name;
    private final String skin;

    public NPCData(int index, String name, Location location, String skinUsername) {
        this.index = index;
        this.name = name;
        world = location.getWorld().getName();
        x = location.x();
        y = location.y();
        z = location.z();
        pitch = location.getPitch();
        yaw = location.getYaw();
        skin = skinUsername;
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getSkin() {
        return skin;
    }
}
