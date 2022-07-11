package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.utilities.CommandName;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.ParseException;

@CommandName("pay")
public class PayCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            sender.sendMessage(Methods.cStr("&cOnly players can execute this command."));
            return true;
        }
        if (args.length < 2) return false;

        Player executor = (Player) sender;
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        double amount;
        try {
            amount = new DecimalFormat("###,###.##").parse(args[1]).doubleValue();
        } catch (ParseException e) {
            return false;
        }
        amount = Math.abs(amount);

        if (EconomyManager.getBalance(executor.getUniqueId()) < amount) {
            Methods.errorMessage("insufficientFunds", executor);
            return true;
        }

        EconomyManager.setBalance(target.getUniqueId(), EconomyManager.getBalance(target.getUniqueId()) + amount);
        EconomyManager.setBalance(executor.getUniqueId(), EconomyManager.getBalance(executor.getUniqueId()) - amount);
        if (target.isOnline()) {
            target.getPlayer().sendMessage(Methods.cStr("&f" + executor.getDisplayName() + " &6has sent you &f$" + Methods.rStr(amount) + "&6."));
            ScoreboardHandler.updateBoard(target.getPlayer());
        }
        executor.sendMessage(Methods.cStr("&f" + executor.getDisplayName() + " &6has received your &f$" + Methods.rStr(amount) + "&6."));
        ScoreboardHandler.updateBoard(executor);
        return true;
    }
}
