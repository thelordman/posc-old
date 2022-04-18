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
        message = event.getMessage().contains("@") ? "I'm a fat bitch" : event.getMessage();
        event.setFormat(Methods.cStr(Methods.playerChatColor(event.getPlayer(), "primary") + event.getPlayer().getDisplayName() + Methods.playerChatColor(event.getPlayer(), "secondary") + ": " + Methods.playerChatColor(event.getPlayer(), "primary") + message));

        if (event.getMessage().contains("@")) {
            CoStrength.minecraftChatChannel.sendMessage("**" + event.getPlayer().getDisplayName() + ":** I'm a fat bitch").queue();
        }
        else {
            CoStrength.minecraftChatChannel.sendMessage("**" + event.getPlayer().getDisplayName() + ":** " + event.getMessage()).queue();
        }
    }
}