package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.utilities.Data;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BountyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if (args.length < 2) {
            Player target = args.length == 0 ? player : Bukkit.getPlayer(args[0]);
            player.sendMessage(Methods.cStr(target.getDisplayName() + "'s &6bounty is &f$" + Methods.rStr(EconomyManager.getBounty(target.getUniqueId())) + "&6."));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        Float amount;
        try {
            amount = Float.valueOf(args[1]);
        } catch (NumberFormatException e) {
            return Methods.errorMessage("notaNumber", player);
        }
        if (target == null | amount.isNaN()) return false;
        if (amount < 100) return Methods.errorMessage("requires100", player);
        if (EconomyManager.getBalance(player.getUniqueId()) < amount) return Methods.errorMessage("insufficientFunds", player);

        EconomyManager.setBalance(player.getUniqueId(), EconomyManager.getBalance(player.getUniqueId()) - amount);
        EconomyManager.setBounty(target.getUniqueId(), EconomyManager.getBounty(target.getUniqueId()) + amount);
        ScoreboardHandler.updateBoard(player);
        Bukkit.broadcastMessage(Methods.cStr(player.getDisplayName() + " &6has added a bounty of &f$" + Methods.rStr(amount) + " &6on " + target.getDisplayName() + "&6.\n&f$" + Methods.rStr(EconomyManager.getBounty(target.getUniqueId())) + " &6total."));
        return true;
    }
}