package me.lord.posc.listeners;

import me.lord.posc.Posc;
import me.lord.posc.data.DataManager;
import me.lord.posc.npc.NPC;
import me.lord.posc.npc.NPCManager;
import me.lord.posc.npc.interaction.NPCInteraction;
import me.lord.posc.utilities.Event;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractEntityListener implements Event {
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (entity instanceof Interaction) {

            NPC npc = NPCManager.getNPCMap().values().stream()
                    .filter(n -> n.getInteractionId() == entity.getUniqueId())
                    .findFirst()
                    .orElse(null);

            if (npc != null) {
                NPCInteraction npcInteraction = NPCInteraction.create(npc);
                if (npcInteraction != null) {
                    NPCInteraction currentInteraction = DataManager.getPlayerData(player).getCurrentInteraction();
                    NPC currentInteractionNPC = null;
                    if (currentInteraction != null) {
                        currentInteractionNPC = currentInteraction.getNPC();
                    }
                    if (currentInteractionNPC != npc) {
                        npcInteraction.setPlayer(player);
                        npcInteraction.setNpc(npc);
                        npcInteraction.callEvent();
                    }
                }
            }
        }
    }
}
