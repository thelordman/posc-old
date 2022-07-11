package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.discord.Discord;
import io.github.thelordman.costrength.utilities.CommandName;
import io.github.thelordman.costrength.utilities.Methods;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@CommandName("broadcast")
public class BroadcastCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 4)) return true;
        if (args.length == 0) return false;
        Bukkit.broadcastMessage(Methods.cStr("&6&lCoStrength &8| &f" + Methods.arrayToString(args)));

        int averagePing = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            averagePing += player.getPing();
        }
        averagePing = averagePing == 0 ? 0 : averagePing / Bukkit.getOnlinePlayers().size();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Broadcast", null, "https://cdn.discordapp.com/attachments/950090391535890442/955545141467287552/CoStrength.png");
        builder.setColor(Color.CYAN);
        builder.setDescription(Methods.replaceColorCodes(Methods.arrayToString(args), '&'));
        builder.setFooter("CoStrength.minehut.gg | Average Ping: " + averagePing + "ms | TPS: " + Methods.rStr(Bukkit.getTPS()[0]));

        Discord.minecraftChatChannel.sendMessageEmbeds(builder.build()).queue();

        return true;
    }
}