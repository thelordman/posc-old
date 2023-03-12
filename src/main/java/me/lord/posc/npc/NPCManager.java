package me.lord.posc.npc;

import me.lord.posc.utilities.IndexMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NPCManager {
    private static final IndexMap<NPC> npcMap = new IndexMap<>();

    public static void createNPC(@Nullable String name, @Nullable Location location, @Nullable String skinUsername, @NotNull Integer index) {
        name = name == null ? "Unnamed" : name;
        NPC npc = new NPC(index, name, location);

        npc.setSkin(skinUsername == null ? name : skinUsername);

        addNPC(npc);
        npc.sendSkinPacket();
    }

    public static void createNPC(@Nullable String name, @Nullable Location location, @Nullable String skinUsername) {
        createNPC(name, location, skinUsername, npcMap.nextIndex());
    }

    public static IndexMap<NPC> getNPCMap() {
        return npcMap;
    }

    public static NPC getNPC(int index) {
        return npcMap.get(index);
    }

    private static void addNPC(NPC npc) {
        npcMap.put(npc);
    }

    public static void removeNPC(int index) {
        npcMap.remove(index);
    }

    public static void sendInitPacketAll(Player player) {
        npcMap.values().forEach(npc -> npc.sendInitPacket(player));
    }

    public static void sendInitPacketAll() {
        Bukkit.getOnlinePlayers().forEach(NPCManager::sendInitPacketAll);
    }

    public static void sendSkinPacketAll(Player player) {
        npcMap.values().forEach(npc -> npc.sendSkinPacket(player));
    }

    public static void sendSkinPacketAll() {
        Bukkit.getOnlinePlayers().forEach(NPCManager::sendSkinPacketAll);
    }
}
