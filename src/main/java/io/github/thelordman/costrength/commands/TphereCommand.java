package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TphereCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) return false;
        Player target = Methods.determineTarget(sender, args[0]);
        Player player = (Player) sender;
        if (sender instanceof Player) {
            target.teleport(player.getLocation());
            player.sendMessage(Methods.cStr("&6You teleported &f" + target.getName() + " &6to your location."));
            target.sendMessage(Methods.cStr("&6You have been teleport to the location of &f" + player.getName() + "&6."));
            return true;
        }
        return false;
    }
}
