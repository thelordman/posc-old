package me.lord.posc.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.lord.posc.discord.events.PlayerChat;
import me.lord.posc.utilities.Event;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class AsyncChatListener implements Event {
    @EventHandler
    public void onAsyncChat(AsyncChatEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        Bukkit.broadcast(TextUtil.c(player.getName() + "&7: &f").append(event.message()));
        PlayerChat.exe(event);
    }
}
