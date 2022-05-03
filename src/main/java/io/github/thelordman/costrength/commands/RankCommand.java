package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.ranks.RankManager;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 7)) return true;
        if (args.length < 2 | args.length > 3) return false;
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (args[1].equals("set")) {
            String rank = args[2];
            RankManager.setRank(target, Methods.getTeamFromString(rank));
            sender.sendMessage(Methods.cStr("&f" + target.getName() + "'s &6rank was set to &f" + rank + "&6."));
            if (target.getPlayer() != null) Methods.updateDisplayName(target.getPlayer());
            return true;
        }
        return false;
    }
}