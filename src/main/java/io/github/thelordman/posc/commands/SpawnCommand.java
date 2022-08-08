package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.Posc;
import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

@CommandName("spawn")
public class SpawnCommand implements CommandExecutor {
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (sender instanceof ConsoleCommandSender && args.length == 0 | (!Methods.hasPermission(((Player) sender).getUniqueId(), 2)) && args.length > 0) return false;
        if (Methods.inCombat((Player) sender)) {
            sender.sendMessage(Methods.cStr("&cYou are combat tagged and cannot run that command."));
            return true;
        }

        Player target = args.length > 0 ? Bukkit.getPlayer(args[0]) : (Player) sender;
        if (target == null) return false;
        if (target == sender) sender.sendMessage(Methods.cStr("&6You will be sent to spawn in &f5 seconds&6."));
        Bukkit.getScheduler().runTaskLater(Posc.get(), () -> spawn(target), target == sender ? 100L : 0L);

        return true;
    }

    private void spawn(Player player) {
        Methods.teleportPlayerToSpawn(player, PlayerTeleportEvent.TeleportCause.COMMAND);
        player.sendMessage(Methods.cStr("&6You have been sent to spawn."));
    }
}