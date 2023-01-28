package me.lord.posc.npc;

import com.mojang.authlib.GameProfile;
import me.lord.posc.Posc;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NPC extends ServerPlayer {
    public NPC(String name, Location location) {
        super(((CraftServer) Bukkit.getServer()).getServer(), ((CraftWorld) Posc.MAIN_WORLD).getHandle(), new GameProfile(UUID.randomUUID(), name));
        setPos(location.getX(), location.getY(), location.getZ());
        setYRot(location.getYaw());
        setXRot(location.getPitch());
    }

    public Location getLocation() {
        return new Location(getBukkitEntity().getWorld(), getX(), getY(), getZ(), getYRot(), getXRot());
    }

    private void sendInitPacket(Player player) {
        ServerGamePacketListenerImpl connection = ((CraftPlayer) player).getHandle().connection;
        connection.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, this));
        connection.send(new ClientboundAddPlayerPacket(this));
        connection.send(new ClientboundRotateHeadPacket(this, (byte) (getYRot() * 256 / 360)));
    }

    public void sendInitPacketNearby() {
        getBukkitEntity().getNearbyEntities(50d, 50d, 50d).stream()
                .filter(e -> e.getType() == EntityType.PLAYER && e.getWorld() == getBukkitEntity().getWorld())
                .map(e -> (Player) e)
                .forEach(this::sendInitPacket);
    }
}
