package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.economy.EconomyManager;
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
            player.sendMessage(Methods.cStr(target.getDisplayName() + "'s &6bounty is &f$" + Methods.rStr(Data.bounty.containsKey(target) ? Data.bounty.get(target) : 0) + "&6."));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        Float amount = Float.valueOf(args[1]);
        if (target == null | amount.isNaN() | amount < 100 | EconomyManager.getBalance(player) > amount) return false;

        EconomyManager.setBalance(player, EconomyManager.getBalance(player) - amount);
        Data.bounty.put(target, Data.bounty.containsKey(target) ? amount : Data.bounty.get(target));
        Bukkit.broadcastMessage(Methods.cStr(player.getDisplayName() + " &6has added a bounty of &f$" + Methods.rStr(amount) + " &6on " + target.getDisplayName() + "&6.\n&f$" + Methods.rStr(Data.bounty.get(target)) + " &6total."));
        return true;
    }
}