package me.lord.posc.data;

import com.mojang.authlib.properties.Property;
import me.lord.posc.npc.NPC;
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

    // Skin Property
    private final String skinValue;
    private final String skinSignature;

    private final String name;

    public NPCData(String name, Location location, Property skinProperty) {
        this.name = name;
        world = location.getWorld().getName();
        x = location.x();
        y = location.y();
        z = location.z();
        pitch = location.getPitch();
        yaw = location.getYaw();
        skinValue = skinProperty == null ? null : skinProperty.getValue();
        skinSignature = skinProperty == null ? null : skinProperty.getSignature();
    }

    public static NPCData fromNPC(NPC npc) {
        return new NPCData(npc.getNameString(), npc.getLocation(), npc.getSkinProperty());
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public String getName() {
        return name;
    }

    public Property getSkin() {
        return skinValue == null | skinSignature == null ? null : new Property("textures", skinValue, skinSignature);
    }
}
