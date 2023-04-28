package me.lord.posc.discord.events;

import io.papermc.paper.advancement.AdvancementDisplay;
import me.lord.posc.discord.Discord;
import me.lord.posc.utilities.PoscEmbedBuilder;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.awt.*;

public class PlayerAdvancement {
    public static void exe(PlayerAdvancementDoneEvent event) {
        if (event.message() == null) return;

        Player player = event.getPlayer();
        AdvancementDisplay display = event.getAdvancement().getDisplay();
        Discord.MINECRAFT_CHAT.sendMessageEmbeds(new PoscEmbedBuilder()
                .setAuthor(player.getName(), null, "https://crafatar.com/avatars/" + player.getUniqueId())
                .setDescription(TextUtil.stripColorCodes(TextUtil.componentToString(event.message()), '&').replaceAll("[\\[\\]]", "**"))
                .setColor(display == null ? Color.BLUE : Color.decode(display.frame().color().asHexString()))
                .build()).queue();
    }
}
