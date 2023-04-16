package me.lord.posc.listeners;

import me.lord.posc.data.DataManager;
import me.lord.posc.discord.events.PlayerQuit;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        DataManager.savePlayerData(event.getPlayer());
        event.quitMessage(TextUtil.c("&7[&c-&7] &f" + event.getPlayer().getName()));

        PlayerQuit.exe(event);
    }
}
