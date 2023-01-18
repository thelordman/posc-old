package me.lord.posc.discord.events;

import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import me.lord.posc.discord.Discord;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin {
    public static void exe(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Discord.MINECRAFT_CHAT.createMessage(EmbedCreateSpec.builder()
                .author(player.getName() + " Joined", null, "https://crafatar.com/avatars/" + player.getUniqueId())
                .color(Color.GREEN)
                .build()).block();
    }
}
