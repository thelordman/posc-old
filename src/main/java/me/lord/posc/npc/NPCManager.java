package me.lord.posc.npc;

import com.mojang.authlib.properties.Property;
import me.lord.posc.Posc;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class NPCManager {
    private static final HashMap<Integer, NPC> npcMap = new HashMap<>();

    public static void createNPC(@Nullable String name, @Nullable Location location, @Nullable Property skinProperty, @NotNull Integer index) {
        name = name == null ? "Unnamed" : name;
        NPC npc = new NPC(index, name, location);
        if (skinProperty != null) npc.setSkin(skinProperty);
        npc.sendInitPacket();
        npc.sendSkinPacket();
        npcMap.put(index, npc);
    }

    public static void createNPC(@Nullable String name, @Nullable Location location, @Nullable String skinUsername, @NotNull Integer index) {
        name = name == null ? "Unnamed" : name;
        NPC npc = new NPC(index, name, location);
        npc.setSkin(skinUsername == null ? name : skinUsername);
        npc.sendInitPacket();
        npc.sendSkinPacket();
        npcMap.put(index, npc);
    }

    public static void createNPC(@Nullable String name, @Nullable Location location, @Nullable String skinUsername) {
        createNPC(name, location, skinUsername, npcMap.keySet().stream().mapToInt(t -> t).max().orElse(-1) + 1);
    }

    public static HashMap<Integer, NPC> getNPCMap() {
        return npcMap;
    }

    public static NPC getTargetNPC(Player player) {
        ServerPlayer nmsPlayer = ((CraftPlayer) player).getHandle();

        Vec3 start = nmsPlayer.getEyePosition(1.0f);
        Vec3 direction = nmsPlayer.getLookAngle();
        Vec3 end = start.add(direction.x * 10d, direction.y * 10d, direction.z * 10d);

        nmsPlayer.getBoundingBox().expandTowards(direction.x * 10d, direction.y * 10d, direction.z * 10d).inflate(1.0d, 1.0d, 1.0d);

        double distance = 0.0d;
        NPC result = null;

        for (NPC npc : NPCManager.npcMap.values()) {
            AABB aabb = npc.getBoundingBox().inflate(0.0f, 0.0f, 0.0f);
            Optional<Vec3> rayTraceResult = aabb.clip(start, end);

            if (rayTraceResult.isPresent()) {
                Vec3 rayTrace = rayTraceResult.get();
                double distanceTo = start.distanceToSqr(rayTrace);
                if (distanceTo < distance || distance == 0.0d) {
                    result = npc;
                    distance = distanceTo;
                }
            }
        }

        return result;
    }

    public static boolean isNPC(Entity entity) {
        Posc.LOGGER.info("entity might be null");
        if (entity == null) return false;
        Posc.LOGGER.info("entity != null");
        net.minecraft.world.entity.Entity nmsEntity = ((CraftEntity) entity).getHandle();
        Posc.LOGGER.info("entity successfully casted to nms");
        if (nmsEntity instanceof NPC) {
            Posc.LOGGER.info("entity was instance of NPC");
            return true;
        }
        Posc.LOGGER.info("entity was NOT instance of NPC");
        return false;
    }

    public static NPC getNPC(Entity entity) {
        if (!isNPC(entity)) return null;
        return (NPC) ((CraftEntity) entity).getHandle();
    }

    public static NPC[] getNPC(String name) {
        return npcMap.values().stream()
                .filter(npc -> npc.getNameString().equalsIgnoreCase(name))
                .sorted(Comparator.comparingInt(NPC::getIndex))
                .toArray(NPC[]::new);
    }

    public static NPC getNPC(int index) {
        return npcMap.get(index);
    }

    public static void removeNPC(int index) {
        NPC npc = npcMap.get(index);
        if (npc != null) {
            npc.sendRemovePacket();
        }
        npcMap.remove(index);
    }

    public static void sendInitPacketAll(Player player) {
        npcMap.values().forEach(npc -> npc.sendInitPacket(player));
    }

    public static void sendInitPacketAll() {
        Bukkit.getOnlinePlayers().forEach(NPCManager::sendInitPacketAll);
    }

    public static void sendRemovePacketAll(Player player) {
        npcMap.values().forEach(npc -> npc.sendRemovePacket(player));
    }

    public static void sendRemovePacketAll() {
        Bukkit.getOnlinePlayers().forEach(NPCManager::sendRemovePacketAll);
    }

    public static void sendSkinPacketAll(Player player) {
        npcMap.values().forEach(npc -> npc.sendSkinPacket(player));
    }

    public static void sendSkinPacketAll() {
        Bukkit.getOnlinePlayers().forEach(NPCManager::sendSkinPacketAll);
    }
}
