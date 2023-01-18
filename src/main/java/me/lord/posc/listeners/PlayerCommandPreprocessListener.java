package me.lord.posc.listeners;

import me.lord.posc.discord.Discord;
import me.lord.posc.utilities.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.time.Instant;

public class PlayerCommandPreprocessListener implements Event {
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        long time = Instant.now().getEpochSecond();
        Discord.MINECRAFT_LOGS.createMessage("<t:" + time + ":d> " + "<t:" + time + ":T> **" + event.getPlayer().getName() + ":** `" + event.getMessage() + "`").block();
    }
}
