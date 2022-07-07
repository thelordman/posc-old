package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.utilities.Methods;
import io.github.thelordman.costrength.utilities.data.PlayerDataManager;
import io.github.thelordman.costrength.utilities.data.Rank;
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
            if(Methods.doesRankExist(args[2].toUpperCase())) {
                String rank = Rank.valueOf(args[2].toUpperCase()).name.isEmpty() ? "Default" : Rank.valueOf(args[2].toUpperCase()).name;
                PlayerDataManager.getPlayerData(target.getUniqueId()).setRank(Rank.valueOf(args[2].toUpperCase()));
                sender.sendMessage(Methods.cStr("&f" + target.getName() + "'s &6rank was set to &f" + rank + "&6."));
                if (target.isOnline()) {
                    Methods.updateDisplayName(target.getPlayer());
                    target.getPlayer().sendMessage(Methods.cStr("&f" + sender.getName() + " &6has set your rank to &f" + rank + "&6."));
                }
                return true;
            }
        }
        return false;
    }
}