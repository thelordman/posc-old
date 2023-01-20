package me.lord.posc.discord.events;

import me.lord.posc.discord.Discord;
import me.lord.posc.utilities.TextUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.awt.*;

public class PlayerDeath {
    public static void exe(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Discord.MINECRAFT_CHAT.sendMessageEmbeds(new EmbedBuilder()
                .setAuthor(player.getName() + " Died", null, "https://crafatar.com/avatars/" + player.getUniqueId())
                .setDescription(TextUtil.stripColorCodes(TextUtil.componentToString(event.deathMessage())))
                .setColor(Color.RED)
                .build()).queue();
    }
}
