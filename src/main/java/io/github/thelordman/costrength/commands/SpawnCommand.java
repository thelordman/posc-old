package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.ranks.RankManager;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (sender instanceof ConsoleCommandSender && args.length == 0 | (!RankManager.hasPermission((OfflinePlayer) sender, (byte) 2)) && args.length > 0) return false;

        Player target = args.length > 0 ? Bukkit.getPlayer(args[0]) : (Player) sender;
        if (target == null) return false;

        Methods.teleportPlayerToSpawn(target, PlayerTeleportEvent.TeleportCause.COMMAND);
        target.sendMessage(Methods.cStr("&6You have been sent to spawn. &f" + Methods.locToString(new Location(Bukkit.getWorld("world"), -0.5, -41, 0.5, 0, 0), false)));
        return true;
    }
}