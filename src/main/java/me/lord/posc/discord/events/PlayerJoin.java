package me.lord.posc.discord.events;

import me.lord.posc.discord.Discord;
import me.lord.posc.utilities.PoscEmbedBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.awt.*;

public class PlayerJoin {
    public static void exe(PlayerJoinEvent event) {
        if (Discord.TOKEN != null) {
            Player player = event.getPlayer();
            Discord.MINECRAFT_CHAT.sendMessageEmbeds(new PoscEmbedBuilder()
                    .setAuthor(player.getName() + " Joined", null, "https://crafatar.com/avatars/" + player.getUniqueId())
                    .setColor(Color.GREEN)
                    .build()).queue();
        }
    }
}
