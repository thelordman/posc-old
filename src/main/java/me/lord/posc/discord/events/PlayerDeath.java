package me.lord.posc.discord.events;

import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import me.lord.posc.discord.Discord;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath {
    public static void exe(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Discord.MINECRAFT_CHAT.createMessage(EmbedCreateSpec.builder()
                .author(player.getName() + " Died", null, "https://crafatar.com/avatars/" + player.getUniqueId())
                .description(TextUtil.stripColorCodes(TextUtil.componentToString(event.deathMessage())))
                .color(Color.RED)
                .build()).block();
    }
}
