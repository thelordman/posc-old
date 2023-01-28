package me.lord.posc.discord.events;

import me.lord.posc.discord.Discord;
import me.lord.posc.utilities.PoscEmbedBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;

public class PlayerQuit {
    public static void exe(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Discord.MINECRAFT_CHAT.sendMessageEmbeds(new PoscEmbedBuilder()
                .setAuthor(player.getName() + " Left", null, "https://crafatar.com/avatars/" + player.getUniqueId())
                .setColor(Color.RED)
                .build()).queue();
    }
}
