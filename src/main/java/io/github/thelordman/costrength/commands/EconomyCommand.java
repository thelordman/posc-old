package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.utilities.Methods;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.ParseException;

public class EconomyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 2 | args.length > 3) return false;
        Player target = Bukkit.getPlayer(args[0]);
        String executor = sender instanceof Player ? ((Player) sender).getDisplayName() : "Console";


        if (args.length < 3) {
            if (args[1].equals("reset")) {
                EconomyManager.setBalance(target, (float) 0);
                target.sendMessage(Methods.cStr("&6Your balance was reset by " + executor + "&6."));
                if (!(target == sender)) sender.sendMessage(Methods.cStr("&6You reset " + target.getDisplayName() + "&6's balance."));
                ScoreboardHandler.updateBoard(target);
                return true;
            }
            if (args[1].equals("get")) {
                sender.sendMessage(EconomyManager.getBalance(target).toString());
                ScoreboardHandler.updateBoard(target);
                return true;
            }
            return false;
        }

        Float bal = EconomyManager.getBalance(target);
        Float amount;
        DecimalFormat df = new DecimalFormat("###,###.##");
        try {
            amount = df.parse(args[2]).floatValue();
        } catch (ParseException e) {
            return false;
        }
        switch (args[1]) {
            case "set":
                EconomyManager.setBalance(target, amount);
                target.sendMessage(Methods.cStr("&6Your balance was set to &f$" + df.format(amount) + " &6by " + executor + "&6."));
                if (!(target == sender)) sender.sendMessage(Methods.cStr("&6You set " + target.getDisplayName() + "&6's balance to &f$" + df.format(amount) + "&6."));
                break;
            case "add", "put":
                EconomyManager.setBalance(target, bal + amount);
                target.sendMessage(Methods.cStr("&6" + executor + " &6has added &f$" + df.format(amount) + " &6to your balance."));
                if (!(target == sender)) sender.sendMessage(Methods.cStr("&6You added &f$" + df.format(amount) + " &6to " + target.getDisplayName() + "&6's balance."));
                break;
            case "subtract", "remove":
                EconomyManager.setBalance(target, bal - amount);
                target.sendMessage(Methods.cStr("&6" + executor + " &6has removed &f$" + df.format(amount) + " &6from your balance."));
                if (!(target == sender)) sender.sendMessage(Methods.cStr("&6You removed &f$" + df.format(amount) + " &6from " + target.getDisplayName() + "&6's balance."));
                break;
            case "multiply", "times":
                EconomyManager.setBalance(target, bal * amount);
                target.sendMessage(Methods.cStr("&6" + executor + " &6has multiplied your balance by &f" + df.format(amount) + "&6."));
                if (!(target == sender)) sender.sendMessage(Methods.cStr("&6You multiplied" + target.getDisplayName() + "&6's balance by &f" + df.format(amount) + "&6."));
                break;
            case "divide", "fraction":
                EconomyManager.setBalance(target, bal / amount);
                target.sendMessage(Methods.cStr("&6" + executor + " &6has divided your balance by &f" + df.format(amount) + "&6."));
                if (!(target == sender)) sender.sendMessage(Methods.cStr("&6You divided" + target.getDisplayName() + "&6's balance by &f" + df.format(amount) + "&6."));
                break;
            case "power", "pow":
                EconomyManager.setBalance(target, (float) Math.pow(bal, amount));
                target.sendMessage(Methods.cStr("&6" + executor + " &6has set your balance to the power of &f" + df.format(amount) + "&6."));
                if (!(target == sender)) sender.sendMessage(Methods.cStr("&6You set" + target.getDisplayName() + "&6's balance to the power of &f" + df.format(amount) + "&6."));
                break;
            case "squareroot", "sqrt":
                EconomyManager.setBalance(target, (float) Math.sqrt(amount));
                target.sendMessage(Methods.cStr("&6" + executor + " &6has set your balance to the squareroot of &f" + df.format(amount) + "&6."));
                if (!(target == sender)) sender.sendMessage(Methods.cStr("&6You set" + target.getDisplayName() + "&6's balance to the squareroot of &f" + df.format(amount) + "&6."));
                break;
            default: return false;
        }
        ScoreboardHandler.updateBoard(target);
        return true;
    }
}
