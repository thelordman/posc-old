package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.items.Kit;
import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandName("joinkit")
public class JoinkitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 4)) return true;
        Player player = (Player) sender;
        Player target = args.length == 0 ? player : Bukkit.getPlayer(args[0]);
        if (target == null) return false;

        Kit.joinKit(target);
        sender.sendMessage(Methods.cStr(target.getDisplayName() + " &6has been given join kit."));
        return true;
    }
}
