package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LevelCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 7)) return true;
        if (args.length < 2 | args.length > 3) return false;
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        String executor = sender instanceof Player ? ((Player) sender).getDisplayName() : "Console";

        if (args.length < 3) {
            if (args[1].equals("reset")) {
                EconomyManager.setLevel(target.getUniqueId(), 0);
                EconomyManager.setXp(target.getUniqueId(), 0d);
                if (target.isOnline()) {
                    target.getPlayer().sendMessage(Methods.cStr("&6Your level was reset by " + executor + "&6."));
                    ScoreboardHandler.updateBoard(target.getPlayer());
                }
                if (!(target == sender)) sender.sendMessage(Methods.cStr("&6You reset " + target.getName() + "&6's level."));
                return true;
            }
            if (args[1].equals("get")) {
                sender.sendMessage(Integer.toString(EconomyManager.getLevel(target.getUniqueId())));
                if (target.isOnline()) ScoreboardHandler.updateBoard(target.getPlayer());
                return true;
            }
            return false;
        }

        String targetMsg;
        String senderMsg;
        int level = EconomyManager.getLevel(target.getUniqueId());
        Double amount;
        double math;
        DecimalFormat df = new DecimalFormat("###,###.##");
        try {
            amount = df.parse(args[2]).doubleValue();
        } catch (ParseException e) {
            return false;
        }
        switch (args[1]) {
            case "set":
                math = amount;
                targetMsg = "&6Your level was set to &f" + df.format(amount) + " &6by " + executor + "&6.";
                senderMsg = "&6You set &f" + target.getName() + "&6's level to &f" + df.format(amount) + "&6.";
                break;
            case "add", "put":
                math = level + amount;
                targetMsg = "&6" + executor + " &6has added &f" + df.format(amount) + " &6to your level.";
                senderMsg = "&6You added &f" + df.format(amount) + " &6to " + target.getName() + "&6's level.";
                break;
            case "subtract", "remove":
                math = level - amount;
                targetMsg = "&6" + executor + " &6has removed &f" + df.format(amount) + " &6from your level.";
                senderMsg = "&6You removed &f" + df.format(amount) + " &6from " + target.getName() + "&6's level.";
                break;
            case "multiply", "times":
                math = level * amount;
                targetMsg = "&6" + executor + " &6has multiplied your level by &f" + df.format(amount) + "&6.";
                senderMsg = "&6You multiplied" + target.getName() + "&6's level by &f" + df.format(amount) + "&6.";
                break;
            case "divide", "fraction":
                math = level / amount;
                targetMsg = "&6" + executor + " &6has divided your level by &f" + df.format(amount) + "&6.";
                senderMsg = "&6You divided" + target.getName() + "&6's level by &f" + df.format(amount) + "&6.";
                break;
            case "power", "pow":
                math = Math.pow(level, amount);
                targetMsg = "&6" + executor + " &6has set your level to the power of &f" + df.format(amount) + "&6.";
                senderMsg = "&6You set" + target.getName() + "&6's level to the power of &f" + df.format(amount) + "&6.";
                break;
            case "squareroot", "sqrt":
                math = Math.sqrt(amount);
                targetMsg = "&6" + executor + " &6has set your level to the squareroot of &f" + df.format(amount) + "&6.";
                senderMsg = "&6You set" + target.getName() + "&6's level to the squareroot of &f" + df.format(amount) + "&6.";
                break;
            default: return false;
        }
        EconomyManager.setLevel(target.getUniqueId(), (int) math);
        EconomyManager.setXp(target.getUniqueId(), 0d);
        if (target.isOnline()) {
            target.getPlayer().sendMessage(Methods.cStr(targetMsg));
            ScoreboardHandler.updateBoard(target.getPlayer());
            Methods.updateDisplayName(target.getPlayer());
        }
        if (!(target == sender)) sender.sendMessage(Methods.cStr(senderMsg));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return switch (args.length) {
            case 1 -> null;
            case 2 -> StringUtil.copyPartialMatches(args[1], List.of("get", "reset", "set", "add", "take", "multiply", "divide", "power", "squareroot"), new ArrayList<>());
            default -> Collections.emptyList();
        };
    }
}
