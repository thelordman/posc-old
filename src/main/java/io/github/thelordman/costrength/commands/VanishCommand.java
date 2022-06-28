package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.ranks.RankManager;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class VanishCommand implements CommandExecutor {

    public static ArrayList<Player> vanishedPlayers = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 5)) return true;
        if (!(sender instanceof Player) && args.length == 0) return false;

        return toggleVanish(sender, args.length > 0 ? Bukkit.getPlayer(args[0]) : (Player) sender);
    }

    private boolean toggleVanish(CommandSender sender, Player target) {
        if (target == null) {
            return false;
        }

        if (vanishedPlayers.contains(target)) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.showPlayer(CoStrength.get(), target);
            }
            vanishedPlayers.remove(target);
            target.sendMessage(Methods.cStr("&6You are no longer vanished."));
            return true;
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (sender instanceof ConsoleCommandSender || RankManager.permissionLevel(onlinePlayer) < RankManager.permissionLevel((Player) sender)) {
                onlinePlayer.hidePlayer(CoStrength.get(), target);
            } else {
                onlinePlayer.sendMessage(Methods.cStr("&f" + target.getDisplayName() + " &6is now vanished."));
            }
        }

        vanishedPlayers.add(target);
        target.sendMessage(Methods.cStr("&6You have been vanished by &f" + sender.getName() + "&6."));

        if (sender != target) {
            sender.sendMessage(Methods.cStr("&6You have vanished &f" + target.getName() + "&6."));
        }
        return true;
    }
}