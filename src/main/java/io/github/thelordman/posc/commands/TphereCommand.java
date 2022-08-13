package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import io.github.thelordman.posc.utilities.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandName("tphere")
public class TphereCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Rank.hasPermission(sender, (byte) 4)) return true;
        if (args.length < 1) return false;
        Player target = Bukkit.getPlayer(args[0]);
        Player player = (Player) sender;
        if (sender instanceof Player && !(target == null)) {
            target.teleport(player.getLocation());
            player.sendMessage(Methods.cStr("&6You teleported &f" + target.getName() + " &6to your location."));
            target.sendMessage(Methods.cStr("&6You have been teleport to the location of &f" + player.getName() + "&6."));
            return true;
        }
        return false;
    }
}
