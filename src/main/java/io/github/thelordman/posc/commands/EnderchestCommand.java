package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandName("enderchest")
public class EnderchestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (Methods.checkDonatorCommandPermission(sender, (byte) 1)) return true;
        if (sender instanceof ConsoleCommandSender) sender.sendMessage(Methods.cStr("&cThis command can only be used by players."));

        Player player = (Player) sender;
        if (args.length == 0) {
            sender.sendMessage(Methods.cStr("&6Opening your enderchest."));
            player.openInventory(player.getEnderChest());
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) return false;
        sender.sendMessage(Methods.cStr("&6Opening &f" + target.getDisplayName() + "'s &6enderchest."));
        player.openInventory(target.getEnderChest());
        return true;
    }
}