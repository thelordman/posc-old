package me.lord.posc.listeners;

import me.lord.posc.data.DataManager;
import me.lord.posc.discord.events.PlayerJoin;
import me.lord.posc.npc.NPCManager;
import me.lord.posc.utilities.Event;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoinListener implements Event {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) DataManager.getGlobal().incrementTotalUsers();
        DataManager.loadPlayerData(event.getPlayer());

        NPCManager.sendInitPacketAll(event.getPlayer());

        event.joinMessage(TextUtil.c("&7[&a+&7] &f" + event.getPlayer().getName() + (event.getPlayer().hasPlayedBefore() ? "" : " &8| &6" + TextUtil.ordinal(DataManager.getGlobal().getTotalUsers()) + " join")));

        PlayerJoin.exe(event);
    }
}
