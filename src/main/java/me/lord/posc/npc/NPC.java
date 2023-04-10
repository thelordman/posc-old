package me.lord.posc.npc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.lord.posc.Posc;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class NPC extends ServerPlayer {
    private final int index;

    private final int interactionId;

    protected NPC(int index, @NotNull String name, @NotNull Location location) {
        super(((CraftServer) Bukkit.getServer()).getServer(),
                ((CraftWorld) Posc.MAIN_WORLD).getHandle(),
                new GameProfile(UUID.randomUUID(), name));

        isRealPlayer = false;

        this.index = index;

        setPos(location.getX(), location.getY(), location.getZ());
        setYRot(location.getYaw());
        setXRot(location.getPitch());

        Interaction interaction = (Interaction) Posc.MAIN_WORLD.spawnEntity(location, EntityType.INTERACTION);
        interaction.setInteractionHeight(2.0f);
        interactionId = interaction.getEntityId();
    }

    public int getIndex() {
        return index;
    }

    public int getInteractionId() {
        return interactionId;
    }

    // TODO: If possible, find a way to update the name without having to create an entirely new object
    public void setName(String name) {
        sendRemovePacket();
        NPC npc = new NPC(getIndex(), name, getLocation());
        npc.getGameProfile().getProperties().putAll(getGameProfile().getProperties());
        npc.sendInitPacket();
        NPCManager.getNPCMap().put(index, npc);
    }

    public String getNameString() {
        return getGameProfile().getName();
    }

    public Location getLocation() {
        return new Location(getBukkitEntity().getWorld(), getX(), getY(), getZ(), getYRot(), getXRot());
    }

    public Property getSkinProperty() {
        Collection<Property> properties = getGameProfile().getProperties().get("textures");
        return properties.stream()
                .filter(p -> p.getName().equals("textures"))
                .findFirst()
                .orElse(null);
    }

    public void setSkin(Property property) {
        getGameProfile().getProperties().removeAll("textures");
        getGameProfile().getProperties().put("textures", property);
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

                setSkin(new Property("textures", skin, signature));

                return true;
            } else {
                Posc.LOGGER.warning("Couldn't open connection to https://api.ashcon.app/mojang/v2/user/" + username + " (Response code " + connection.getResponseCode() + ": " + connection.getResponseMessage() + ")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void sendInitPacket(Player player) {
        ServerGamePacketListenerImpl connection = ((CraftPlayer) player).getHandle().connection;
        connection.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, this));
        connection.send(new ClientboundAddPlayerPacket(this));
        connection.send(new ClientboundRotateHeadPacket(this, (byte) (getYRot() * 256 / 360)));
        SynchedEntityData data = getEntityData();
        data.set(new EntityDataAccessor<>(17, EntityDataSerializers.BYTE), (byte) 126);
        connection.send(new ClientboundSetEntityDataPacket(getId(), data.getNonDefaultValues()));
    }

    public void sendInitPacket() {
        Bukkit.getOnlinePlayers().forEach(this::sendInitPacket);
    }

    public void sendRotateHeadPacket(Player player) {
        ((CraftPlayer) player).getHandle().connection.send(new ClientboundRotateHeadPacket(this, (byte) (getYRot() * 256 / 360)));
    }

    public void sendRotateHeadPacket() {
        Bukkit.getOnlinePlayers().forEach(this::sendRotateHeadPacket);
    }

    public void sendRemovePacket(Player player) {
        ((CraftPlayer) player).getHandle().connection.send(new ClientboundRemoveEntitiesPacket(getId()));
    }

    public void sendRemovePacket() {
        Bukkit.getOnlinePlayers().forEach(this::sendRemovePacket);
    }

    public void sendNamedSpawnPacket(Player player) {
        ((CraftPlayer) player).getHandle().connection.send(new ClientboundAddPlayerPacket(this));
    }

    public void sendNamedSpawnPacket() {
        Bukkit.getOnlinePlayers().forEach(this::sendNamedSpawnPacket);
    }

    public void sendRemovePlayerPacket(Player player) {
        ((CraftPlayer) player).getHandle().connection.send(new ClientboundPlayerInfoRemovePacket(List.of(getUUID())));
    }

    public void sendRemovePlayerPacket() {
        Bukkit.getOnlinePlayers().forEach(this::sendRemovePlayerPacket);
    }
}
