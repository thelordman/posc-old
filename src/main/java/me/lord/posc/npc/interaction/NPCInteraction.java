package me.lord.posc.npc.interaction;

import me.lord.posc.npc.NPC;
import me.lord.posc.utilities.ReflectionUtil;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public abstract class NPCInteraction {
    private static Class<? extends NPCInteraction>[] interactions;

    private Player player;
    private NPC npc;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }

    public NPC getNPC() {
        return npc;
    }

    public abstract void callEvent();

    public static NPCInteraction create(NPC npc) {
        for (Class<? extends NPCInteraction> interaction : interactions) {
            if (interaction.getAnnotation(NPCIndex.class).id() == npc.getIndex()) {
                try {
                    return interaction.getDeclaredConstructor().newInstance();
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void registerInteractions() {
        interactions = ReflectionUtil.getSubclasses(NPCInteraction.class, "me.lord.posc.npc.interaction");
    }
}
