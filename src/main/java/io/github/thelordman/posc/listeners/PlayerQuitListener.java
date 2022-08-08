package io.github.thelordman.posc.listeners;

import io.github.thelordman.posc.discord.Discord;
import io.github.thelordman.posc.economy.EconomyManager;
import io.github.thelordman.posc.utilities.data.Data;
import io.github.thelordman.posc.utilities.Methods;
import io.github.thelordman.posc.scoreboard.FastBoard;
import io.github.thelordman.posc.utilities.data.DataManager;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;


public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (Methods.inCombat(event.getPlayer())) {
            event.getPlayer().setHealth(0);
            EconomyManager.setBalance(event.getPlayer().getUniqueId(), EconomyManager.getBalance(event.getPlayer().getUniqueId()) / 2d);
            Bukkit.broadcastMessage(Methods.cStr(event.getPlayer().getDisplayName() + " &6chickened and combat logged!"));
        }

        event.setQuitMessage(Methods.cStr("&7[&c-&7] &7" + event.getPlayer().getDisplayName()));

        FastBoard scoreboard = Data.scoreboard.remove(event.getPlayer().getUniqueId());

        if (scoreboard != null) {
            scoreboard.delete();
        }

        DataManager.savePlayerData(event.getPlayer().getUniqueId());

        //Discord
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Methods.replaceColorCodes(event.getPlayer().getDisplayName() + " Left", '§'), null, "https://crafatar.com/avatars/" + event.getPlayer().getUniqueId());
        builder.setColor(Color.RED);

        Discord.minecraftChatChannel.sendMessageEmbeds(builder.build()).queue();
    }
}