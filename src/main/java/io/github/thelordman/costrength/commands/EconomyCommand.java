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
        if (!Methods.checkCommandPermission(sender, (byte) 7)) return true;
        if (args.length < 2 | args.length > 3) return false;
        Player target = Bukkit.getPlayer(args[0]);
        String executor = sender instanceof Player ? ((Player) sender).getDisplayName() : "Console";
        if (target == null) return false;

        if (args.length < 3) {
            if (args[1].equals("reset")) {
                EconomyManager.setBalance(target, 0f);
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

        String targetMsg;
        String senderMsg;
        Float bal = EconomyManager.getBalance(target);
        Float amount;
        Float math;
        DecimalFormat df = new DecimalFormat("###,###.##");
        try {
            amount = df.parse(args[2]).floatValue();
        } catch (ParseException e) {
            return false;
        }
        switch (args[1]) {
            case "set":
                math = amount;
                targetMsg = "&6Your balance was set to &f$" + df.format(amount) + " &6by " + executor + "&6.";
                senderMsg = "&6You set &f" + target.getDisplayName() + "&6's balance to &f$" + df.format(amount) + "&6.";
                break;
            case "add", "put":
                math = bal + amount;
                targetMsg = "&6" + executor + " &6has added &f$" + df.format(amount) + " &6to your balance.";
                senderMsg = "&6You added &f$" + df.format(amount) + " &6to " + target.getDisplayName() + "&6's balance.";
                break;
            case "subtract", "remove":
                math = bal - amount;
                targetMsg = "&6" + executor + " &6has removed &f$" + df.format(amount) + " &6from your balance.";
                senderMsg = "&6You removed &f$" + df.format(amount) + " &6from " + target.getDisplayName() + "&6's balance.";
                break;
            case "multiply", "times":
                math = bal * amount;
                targetMsg = "&6" + executor + " &6has multiplied your balance by &f" + df.format(amount) + "&6.";
                senderMsg = "&6You multiplied" + target.getDisplayName() + "&6's balance by &f" + df.format(amount) + "&6.";
                break;
            case "divide", "fraction":
                math = bal / amount;
                targetMsg = "&6" + executor + " &6has divided your balance by &f" + df.format(amount) + "&6.";
                senderMsg = "&6You divided" + target.getDisplayName() + "&6's balance by &f" + df.format(amount) + "&6.";
                break;
            case "power", "pow":
                math = (float) Math.pow(bal, amount);
                targetMsg = "&6" + executor + " &6has set your balance to the power of &f" + df.format(amount) + "&6.";
                senderMsg = "&6You set" + target.getDisplayName() + "&6's balance to the power of &f" + df.format(amount) + "&6.";
                break;
            case "squareroot", "sqrt":
                math = (float) Math.sqrt(amount);
                targetMsg = "&6" + executor + " &6has set your balance to the squareroot of &f" + df.format(amount) + "&6.";
                senderMsg = "&6You set" + target.getDisplayName() + "&6's balance to the squareroot of &f" + df.format(amount) + "&6.";
                break;
            default: return false;
        }
        EconomyManager.setBalance(target, math);
        target.sendMessage(Methods.cStr(targetMsg));
        if (!(target == sender)) sender.sendMessage(Methods.cStr(senderMsg));
        ScoreboardHandler.updateBoard(target);
        return true;
    }
}
