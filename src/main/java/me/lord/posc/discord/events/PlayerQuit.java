package me.lord.posc.discord.events;

import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import me.lord.posc.discord.Discord;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit {
    public static void exe(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Discord.MINECRAFT_CHAT.createMessage(EmbedCreateSpec.builder()
                .author(player.getName() + " Left", null, "https://crafatar.com/avatars/" + player.getUniqueId())
                .color(Color.RED)
                .build()).block();

    }
}
