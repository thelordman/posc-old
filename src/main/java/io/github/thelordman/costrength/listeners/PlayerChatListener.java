package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message;
        message = event.getMessage().contains("@") | event.getMessage().contains("jk") ? "I'm a fat bitch" : event.getMessage();
        event.setFormat(Methods.cStr(event.getPlayer().getDisplayName() + Methods.playerChatColor(event.getPlayer(), (byte) 1) + ": " + Methods.playerChatColor(event.getPlayer(), (byte) 0) + message));

        CoStrength.minecraftChatChannel.sendMessage(Methods.replaceColorCodes("**" + event.getPlayer().getDisplayName() + ":** " + message, '§')).queue();
    }
}