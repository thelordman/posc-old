package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player ? ((Player) sender).getPlayer() : null;

        if (args.length == 0) {
            if (player == null) return false;
            sender.sendMessage(Methods.cStr("&6Your balance: &f$" + Methods.rStr(EconomyManager.getBalance(player.getUniqueId()))));
        }
        else {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            sender.sendMessage(Methods.cStr("&6" + target.getName() + "&6's balance: &f$" + Methods.rStr(EconomyManager.getBalance(target.getUniqueId()))));
        }
        return true;
    }
}
