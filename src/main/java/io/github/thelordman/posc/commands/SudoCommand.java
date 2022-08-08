package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.discord.Discord;
import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@CommandName("sudo")
public class SudoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 7)) return true;

        if (args[0].isEmpty() | args[1].isEmpty()) return false;
        Player target = Bukkit.getPlayer(args[0]);

        String[] message = Arrays.copyOfRange(args, 1, args.length);
        if (args[1].startsWith("c:")) {
            Bukkit.broadcastMessage(Methods.cStr(target.getDisplayName() + Methods.playerChatColor(target, (byte) 1) +
                    ": " + Methods.playerChatColor(target, (byte) 0) + String.join(" ", message)
                    .replaceFirst("c:", "")));
            Discord.minecraftChatChannel.sendMessage(Methods.replaceColorCodes("**" + target.getDisplayName() + ":** " +
                    String.join(" ", message).replaceFirst("c:", ""), '§')).queue();
            return true;
        } else {
            return target.performCommand(String.join(" ", message));
        }
    }
}