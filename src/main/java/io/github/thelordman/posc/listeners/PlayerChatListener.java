package io.github.thelordman.posc.listeners;

import io.github.thelordman.posc.discord.Discord;
import io.github.thelordman.posc.punishments.PunishmentManager;
import io.github.thelordman.posc.utilities.Methods;
import io.github.thelordman.posc.utilities.Rank;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;

public class PlayerChatListener implements Listener {
    private final HashMap<Player, Long> lastMessage = new HashMap<>();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (PunishmentManager.isMuted(event.getPlayer().getUniqueId())) {
            event.getPlayer().sendMessage(Methods.cStr("&cYou are muted and cannot talk."));
            event.setCancelled(true);
            return;
        }

        if (System.currentTimeMillis() - lastMessage.getOrDefault(event.getPlayer(), 0L) < 2000L && !Rank.hasDonorPermission(event.getPlayer(), 1)) {
            event.getPlayer().sendMessage(Methods.cStr("&cYou must wait at least 2 seconds before chatting\n&cBypass with /buy"));
            event.setCancelled(true);
            return;
        }
        lastMessage.put(event.getPlayer(), System.currentTimeMillis());

        String message = event.getMessage().contains("@") | event.getMessage().contains(";-;") | event.getMessage().contains("muh") ? "I'm a fat bitch" : event.getMessage();
        message.replace("%", "%%");
        event.setFormat(Methods.cStr(event.getPlayer().getDisplayName() + Methods.playerChatColor(event.getPlayer(), (byte) 1) + ": " + Methods.playerChatColor(event.getPlayer(), (byte) 0) + message));

        Discord.minecraftChatChannel.sendMessage(Methods.replaceColorCodes("**" + event.getPlayer().getDisplayName() + ":** " + message, '§')).queue();
    }
}