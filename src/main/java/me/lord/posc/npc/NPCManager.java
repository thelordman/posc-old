package me.lord.posc.npc;

import me.lord.posc.utilities.IndexMap;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

public class NPCManager {
    private static final IndexMap<NPC> npcMap = new IndexMap<>();

    public static void createNPC(@Nullable String name, @Nullable Location location, @Nullable String skinUsername) {
        NPC npc = new NPC(npcMap.highestIndex(), name == null ? "Unnamed" : name, location);

        if (skinUsername != null) {
            npc.setSkin(skinUsername);
        }

        addNPC(npc);
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
}
