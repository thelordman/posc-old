package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.discord.Discord;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BroadcastCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 4)) return true;
        if (args.length == 0) return false;
        Bukkit.broadcastMessage(Methods.cStr("&6&lCoStrength &8| &f" + Methods.arrayToString(args)));
        Discord.minecraftChatChannel.sendMessage("**Broadcast**").queue();
        Discord.minecraftChatChannel.sendMessage("**" + Methods.arrayToString(args) + "**").queue();
        Discord.minecraftChatChannel.sendMessage("**Broadcast**").queue();
        return true;
    }
}