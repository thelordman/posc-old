package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.discord.Discord;
import io.github.thelordman.costrength.utilities.Methods;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import java.awt.*;

public class PlayerKickListener implements Listener {
    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Methods.replaceColorCodes(event.getPlayer().getDisplayName() + " Got Kicked By " + event.getCause().name(), '§'), null, "https://crafatar.com/avatars/" + event.getPlayer().getUniqueId());
        builder.setColor(Color.RED);
        builder.setDescription(event.getReason());

        Discord.minecraftChatChannel.sendMessageEmbeds(builder.build()).queue();
    }
}
