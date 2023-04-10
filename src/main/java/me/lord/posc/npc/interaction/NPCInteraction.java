package me.lord.posc.npc.interaction;

import me.lord.posc.npc.NPC;
import org.bukkit.entity.Player;

public abstract class NPCInteraction {
    Player player;
    NPC npc;

    public abstract void callEvent();

    public static NPCInteraction create(Player player, NPC npc) {
        return null;
    }
}
