package me.lord.posc.discord.events;

import me.lord.posc.discord.Discord;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.time.Instant;

public class PlayerCommand {
    public static void exe(PlayerCommandPreprocessEvent event) {
        long time = Instant.now().getEpochSecond();
        Discord.MINECRAFT_LOGS.sendMessage("<t:" + time + ":d> " + "<t:" + time + ":T> **" + event.getPlayer().getName() + ":** `" + event.getMessage() + "`").queue();
    }
}
