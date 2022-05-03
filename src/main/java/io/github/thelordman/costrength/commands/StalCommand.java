package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.ranks.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StalCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender && args.length == 0 | (!RankManager.hasPermission((OfflinePlayer) sender, (byte) 4)) && args.length > 0) return false;
        Player target = args.length > 0 ? Bukkit.getPlayer(args[0]) : (Player) sender;
        target.playSound(target, Sound.MUSIC_DISC_STAL, 1f, 1f);
        return true;
    }
}
