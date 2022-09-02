package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandName("Fly")
public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Rank.hasPermission(sender, 5)) return true;
        Player target = args.length > 0 ? Bukkit.getPlayer(args[0]) : (Player) sender;
        if (target == null) return false;

        target.setFlying(!target.isFlying());
        target.sendMessage("&6Flight has been set to " + (target.isFlying() ? "&cFalse" : "&aTrue" + (target == sender ? "&6." : " &6by &f" + sender.getName() + "&6.")));
        return true;
    }
}
