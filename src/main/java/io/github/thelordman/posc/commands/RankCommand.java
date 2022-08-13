package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import io.github.thelordman.posc.utilities.data.DataManager;
import io.github.thelordman.posc.utilities.Rank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CommandName("rank")
public class RankCommand implements CommandExecutor,TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Rank.hasPermission(sender, (byte) 7)) return true;
        if (args.length < 2 | args.length > 3) return false;
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (args[1].equals("set")) {
            if(Methods.doesRankExist(args[2].toUpperCase())) {
                String rank = Rank.valueOf(args[2].toUpperCase()).name.isEmpty() ? "Default" : Rank.valueOf(args[2].toUpperCase()).name;
                DataManager.getPlayerData(target.getUniqueId()).setRank(Rank.valueOf(args[2].toUpperCase()));
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return switch (args.length) {
            case 1 -> null;
            case 2 -> List.of("set");
            case 3 -> StringUtil.copyPartialMatches(args[2], List.of("default", "vip", "mvp", "elite", "legend", "trial_developer", "jrmod", "builder",
                    "mod", "srmod", "developer", "head_builder", "admin", "head_developer", "owner"), new ArrayList<>());
            default -> Collections.emptyList();
        };
    }
}