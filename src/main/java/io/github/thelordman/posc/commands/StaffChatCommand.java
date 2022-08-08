package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.discord.Discord;
import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@CommandName("staffchat")
public class StaffChatCommand implements CommandExecutor,TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 2)) return true;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Methods.hasPermission(player.getUniqueId(), 1)) {
                player.sendMessage(Methods.cStr("&6[Staff Chat] &e" + sender.getName() + "&7: &f" + String.join(" ", args)));
            }
        }
        Discord.staffChatChannel.sendMessage("**" + sender.getName() + ":** " + String.join(" ", args)).queue();

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
