package me.lord.posc.listeners;

import me.lord.posc.data.DataManager;
import me.lord.posc.utilities.Event;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerQuitListener implements Event {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        DataManager.savePlayerData(event.getPlayer());
        event.quitMessage(TextUtil.c("&7[&c-&7] &f" + event.getPlayer().getName()));
    }
}
