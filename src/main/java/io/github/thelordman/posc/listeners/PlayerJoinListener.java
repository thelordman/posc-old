package io.github.thelordman.posc.listeners;

import io.github.thelordman.posc.Posc;
import io.github.thelordman.posc.commands.VanishCommand;
import io.github.thelordman.posc.discord.Discord;
import io.github.thelordman.posc.economy.EconomyManager;
import io.github.thelordman.posc.economy.LevelHandler;
import io.github.thelordman.posc.scoreboard.ScoreboardHandler;
import io.github.thelordman.posc.items.Kit;
import io.github.thelordman.posc.utilities.Methods;
import io.github.thelordman.posc.utilities.data.Data;
import io.github.thelordman.posc.utilities.data.DataManager;
import io.github.thelordman.posc.utilities.data.Rank;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.awt.*;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        DataManager.loadPlayerData(event.getPlayer().getUniqueId());
        if (!event.getPlayer().hasPlayedBefore()) {
            Data.newPlayers.add(event.getPlayer());
            Kit.joinKit(event.getPlayer());
        }

        for (Player vanishedPlayer : VanishCommand.getVanishedPlayers()) {
            int permissionLevel = Rank.getRank(vanishedPlayer.getUniqueId()).permissionLevel;
            if (event.getPlayer().equals(vanishedPlayer)) {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    if (Rank.getRank(onlinePlayer.getUniqueId()).permissionLevel < Rank.getRank(event.getPlayer().getUniqueId()).permissionLevel) {
                        onlinePlayer.hidePlayer(Posc.get(), event.getPlayer());
                    }
                }
                event.joinMessage(null);
                event.getPlayer().sendMessage(Methods.cStr("&6You are currently in vanish."));
                continue;
            }
            if (Rank.getRank(event.getPlayer().getUniqueId()).permissionLevel < permissionLevel) {
                event.getPlayer().hidePlayer(Posc.get(), vanishedPlayer);
            }
        }

        if (EconomyManager.getLevel(event.getPlayer().getUniqueId()) == 0) EconomyManager.setLevel(event.getPlayer().getUniqueId(), 1);
        LevelHandler.xp(event.getPlayer());

        Methods.updateDisplayName(event.getPlayer());
        event.getPlayer().setPlayerListHeaderFooter(Methods.cStr("\n  &6&lCoStrength.minehut.gg  \n"), Methods.cStr("\n&6/help &8| &6/dc &8| &6/buy"));

        String message = event.getPlayer().hasPlayedBefore() ? "&7[&a+&7] &7" + event.getPlayer().getDisplayName() : "&7[&a+&7] &7" + event.getPlayer().getDisplayName() + " &6#" + Methods.rStr((double) DataManager.getAllPlayers());
        if (!event.getPlayer().hasPlayedBefore()) DataManager.incrementAllPlayers();
        event.setJoinMessage(Methods.cStr(message));

        ScoreboardHandler.updateBoard(event.getPlayer());

        //Discord
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Methods.replaceColorCodes(event.getPlayer().getDisplayName() + " Joined", '§'), null, "https://crafatar.com/avatars/" + event.getPlayer().getUniqueId());
        builder.setColor(Color.GREEN);

        Discord.minecraftChatChannel.sendMessageEmbeds(builder.build()).queue();
    }
}