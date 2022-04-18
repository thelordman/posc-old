package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.CoStrength;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import java.awt.*;

public class PlayerKickListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerKickEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getPlayer().getDisplayName() + " Got Kicked By " + event.getCause().name(), null, "https://crafatar.com/avatars/" + event.getPlayer().getUniqueId());
        builder.setColor(Color.RED);
        if (event.getReason() != null) {
            builder.setDescription(event.getReason());
        } else {
            builder.setDescription("He took the L");
        }

        CoStrength.minecraftChatChannel.sendMessageEmbeds(builder.build()).queue();
    }
}
