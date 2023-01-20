package me.lord.posc.discord.events;

import me.lord.posc.discord.Discord;
import me.lord.posc.utilities.TextUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.awt.*;

public class PlayerJoin {
    public static void exe(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Discord.MINECRAFT_CHAT.sendMessageEmbeds(new EmbedBuilder()
                .setAuthor(player.getName() + " Joined", null, "https://crafatar.com/avatars/" + player.getUniqueId())
                .setDescription(TextUtil.stripColorCodes(TextUtil.componentToString(event.joinMessage())))
                .setColor(Color.GREEN)
                .build()).queue();

    }
}
