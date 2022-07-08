package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.discord.Discord;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 2)) return true;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Methods.hasPermission(player.getUniqueId(), 1)) {
                player.sendMessage(Methods.cStr("&6[Staff Chat] &e" + sender.getName() + "&7: &f" + String.join(" ", args)));
            }
        }
        Discord.staffChatChannel.sendMessage(String.join(" ", args)).queue();

        return true;
    }
}
