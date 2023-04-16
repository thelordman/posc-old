package me.lord.posc.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.lord.posc.discord.events.PlayerChat;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AsyncChatListener implements Listener {
    @EventHandler
    public void onAsyncChat(AsyncChatEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        Bukkit.broadcast(TextUtil.c(player.getDisplayName() + "&7: &f").append(event.message()));
        PlayerChat.exe(event);
    }
}
