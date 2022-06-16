package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.discord.Discord;
import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.utilities.Data;
import io.github.thelordman.costrength.utilities.Methods;
import io.github.thelordman.costrength.scoreboard.FastBoard;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
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

        event.getPlayer().setStatistic(Statistic.USE_ITEM, Material.GOLD_NUGGET, EconomyManager.getBalance(event.getPlayer().getUniqueId()).intValue());
        event.getPlayer().setStatistic(Statistic.USE_ITEM, Material.SPYGLASS, EconomyManager.getBounty(event.getPlayer().getUniqueId()).intValue());
        event.getPlayer().setStatistic(Statistic.USE_ITEM, Material.EXPERIENCE_BOTTLE, EconomyManager.getXp(event.getPlayer().getUniqueId()).intValue());
        event.getPlayer().setStatistic(Statistic.USE_ITEM, Material.FIREWORK_ROCKET, EconomyManager.getLevel(event.getPlayer().getUniqueId()));
        event.getPlayer().setStatistic(Statistic.USE_ITEM, Material.WOODEN_SWORD, EconomyManager.getKillstreak(event.getPlayer().getUniqueId()));

        event.setQuitMessage(Methods.cStr("&7[&c-&7] &7" + event.getPlayer().getDisplayName()));

        FastBoard scoreboard = Data.scoreboard.remove(event.getPlayer().getUniqueId());

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