package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.discord.Discord;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.utilities.Methods;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.awt.*;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Methods.updateDisplayName(event.getPlayer());
        event.getPlayer().setPlayerListHeaderFooter(Methods.cStr("\n  &6&lCoStrength.minehut.gg  \n"), Methods.cStr("\n&6/help &8| &6/dc &8| &6/buy"));

        String message = event.getPlayer().hasPlayedBefore() ? "&7[&a+&7] &7" + event.getPlayer().getDisplayName() : "&7[&a+&7] &7" + event.getPlayer().getDisplayName() + " &6#" + Bukkit.getServer().getOfflinePlayers().length;
        event.setJoinMessage(Methods.cStr(message));

        if (EconomyManager.getBalance(event.getPlayer().getUniqueId()) == null) EconomyManager.setBalance(event.getPlayer().getUniqueId(), 0f);
        if (EconomyManager.getKillstreak(event.getPlayer().getUniqueId()) == null) EconomyManager.setKillstreak(event.getPlayer().getUniqueId(), 0);
        if (EconomyManager.getLevel(event.getPlayer().getUniqueId()) == null) EconomyManager.setLevel(event.getPlayer().getUniqueId(), 1);
        if (EconomyManager.getXp(event.getPlayer().getUniqueId()) == null) EconomyManager.setXp(event.getPlayer().getUniqueId(), 0f);

        ScoreboardHandler.updateBoard(event.getPlayer());

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Methods.replaceColorCodes(event.getPlayer().getDisplayName() + " Joined", '§'), null, "https://crafatar.com/avatars/" + event.getPlayer().getUniqueId());
        builder.setColor(Color.GREEN);

        Discord.minecraftChatChannel.sendMessageEmbeds(builder.build()).queue();

    }
}