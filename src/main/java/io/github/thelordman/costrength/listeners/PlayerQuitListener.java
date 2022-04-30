package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.discord.Discord;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.utilities.Methods;
import io.github.thelordman.costrength.scoreboard.FastBoard;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;


public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(Methods.cStr("&7[&c-&7] &7" + event.getPlayer().getDisplayName()));

        FastBoard scoreboard = ScoreboardHandler.scoreboard.remove(event.getPlayer().getUniqueId());

        if (scoreboard != null) {
            scoreboard.delete();
        }

        //Discord
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Methods.replaceColorCodes(event.getPlayer().getDisplayName() + " Left", '§'), null, "https://crafatar.com/avatars/" + event.getPlayer().getUniqueId());
        builder.setColor(Color.RED);

        Discord.minecraftChatChannel.sendMessageEmbeds(builder.build()).queue();
    }
}