package me.lord.posc.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.lord.posc.Posc;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

public class NPC {
    private final UUID uuid;
    private final int index;
    private String skin;

    protected NPC(int index, @NotNull String name, @Nullable Location location) {
        UUID uuid = UUID.randomUUID();

        new ServerPlayer(((CraftServer) Bukkit.getServer()).getServer(),
                ((CraftWorld) Posc.MAIN_WORLD).getHandle(),
                new GameProfile(uuid, name));

        this.uuid = uuid;

        this.index = index;

        if (location != null) {
            getPlayer().setPos(location.getX(), location.getY(), location.getZ());
            getPlayer().setYRot(location.getYaw());
            getPlayer().setXRot(location.getPitch());
        }

        sendInitPacket();
    }

    private ServerPlayer getPlayer() {
        return MinecraftServer.getServer().getPlayerList().getPlayer(uuid);
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return getPlayer().displayName;
    }

    public Location getLocation() {
        return new Location(getPlayer().getBukkitEntity().getWorld(), getPlayer().getX(), getPlayer().getY(), getPlayer().getZ(), getPlayer().getYRot(), getPlayer().getXRot());
    }

    public String getSkinUsername() {
        return skin;
    }

    public boolean setSkin(String username) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL("https://api.ashcon.app/mojang/v2/user/" + username).openConnection();
            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String reply = String.join(" ", reader.lines().toList());
                int indexOfValue = reply.indexOf("\"value\": \"");
                int indexOfSignature = reply.indexOf("\"signature\": \"");
                String skin = reply.substring(indexOfValue + 10, reply.indexOf("\"", indexOfValue + 10));
                String signature = reply.substring(indexOfSignature + 14, reply.indexOf("\"", indexOfSignature + 14));

                getPlayer().getGameProfile().getProperties().put("textures", new Property("textures", skin, signature));
                skin = username;

                return true;
            } else {
                Posc.LOGGER.warning("Couldn't open connection to https://api.ashcon.app/mojang/v2/user/" + username + " (Response code " + connection.getResponseCode() + ", " + connection.getResponseMessage() + ")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void sendInitPacket(Player player) {
        ServerGamePacketListenerImpl connection = ((CraftPlayer) player).getHandle().connection;
        connection.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, getPlayer()));
        connection.send(new ClientboundAddPlayerPacket(getPlayer()));
        connection.send(new ClientboundRotateHeadPacket(getPlayer(), (byte) (getPlayer().getYRot() * 256 / 360)));
    }

    public void sendInitPacket() {
        Bukkit.getOnlinePlayers().forEach(this::sendInitPacket);
    }

    public void sendSkinPacket(Player player) {
        SynchedEntityData data = getPlayer().getEntityData();
        data.set(new EntityDataAccessor<>(17, EntityDataSerializers.BYTE), (byte) 126);
        ((CraftPlayer) player).getHandle().connection.send(new ClientboundSetEntityDataPacket(getPlayer().getId(), data.getNonDefaultValues()));
    }

    public void sendSkinPacket() {
        Bukkit.getOnlinePlayers().forEach(this::sendSkinPacket);
    }
}
