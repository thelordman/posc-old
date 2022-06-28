package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.ranks.RankManager;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffchatCommand implements CommandExecutor {
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 1)) return true;
        if (args.length > 0) return false;

        for (Player online : Bukkit.getOnlinePlayers()) {
            if ()
        }

        return true;
    }
}
