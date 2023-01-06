package me.lord.posc.listeners;

import me.lord.posc.utilities.Event;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Event {
    // TODO: Replace Bukkit.getOfflinePlayers().length with a player counting system to increase performance
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.joinMessage(TextUtil.c("&7[&a+&7] &f" + event.getPlayer().getName() + (event.getPlayer().hasPlayedBefore() ? "" : " &8| &6" + TextUtil.ordinal(Bukkit.getOfflinePlayers().length) + " join")));
    }
}
