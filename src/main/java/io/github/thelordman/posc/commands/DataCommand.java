package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.economy.EconomyManager;
import io.github.thelordman.posc.scoreboard.ScoreboardHandler;
import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import io.github.thelordman.posc.utilities.Rank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CommandName("data")
public class DataCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Rank.hasPermission(sender, 7)) return true;
        if (args.length < 5 | !List.of("statistic", "stat", "playerdata", "data").contains(args[1]) |
                !List.of("set", "add", "take", "multiply", "divide", "power", "squareroot").contains(args[3])) return false;
        if (args[1].equals("statistic") | args[1].equals("stat")) {
            if (!List.of(Statistic.values()).toString().contains(args[2].toUpperCase()) | Statistic.valueOf(args[2].toUpperCase()).isSubstatistic()) {
                return false;
            }
        } else if (!List.of("balance", "level", "xp", "killstreak", "bounty").contains(args[2])) return false;

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        double amount;
        try {
            amount = Double.parseDouble(args[4]);
        } catch (NumberFormatException e) {
            return false;
        }
        double bal = args[1].equals("statistic") | args[1].equals("stat") ? target.getStatistic(Statistic.valueOf(args[2].toUpperCase())) :
                switch (args[2]) {
                    case "balance" -> EconomyManager.getBalance(target.getUniqueId());
                    case "level" -> EconomyManager.getLevel(target.getUniqueId());
                    case "xp" -> EconomyManager.getXp(target.getUniqueId());
                    case "killstreak" -> EconomyManager.getKillstreak(target.getUniqueId());
                    case "bounty" -> EconomyManager.getBounty(target.getUniqueId());
                    default -> 0d;
                };

        double math = switch (args[3]) {
            case "set" -> amount;
            case "add" -> bal + amount;
            case "take" -> bal - amount;
            case "multiply" -> bal * amount;
            case "divide" -> bal / amount;
            case "power" -> Math.pow(bal, amount);
            case "squareroot" -> Math.sqrt(amount);
            default -> 0d;
        };

        if (args[1].equals("statistic") | args[1].equals("stat")) {
            target.setStatistic(Statistic.valueOf(args[2].toUpperCase()), (int) math);
        } else switch (args[2]) {
                case "balance" -> EconomyManager.setBalance(target.getUniqueId(), math);
                case "level" -> EconomyManager.setLevel(target.getUniqueId(), (int) math);
                case "xp" -> EconomyManager.setXp(target.getUniqueId(), math);
                case "killstreak" -> EconomyManager.setKillstreak(target.getUniqueId(), (int) math);
                case "bounty" -> EconomyManager.setBounty(target.getUniqueId(), math);
            }

        sender.sendMessage(Methods.cStr("&6Successfully modified the data of &f" + target.getName() + " &6."));
        if (target.isOnline()) ScoreboardHandler.updateBoard(target.getPlayer());

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args.length) {
            case 1 -> {
                return null;
            }
            case 2 -> {
                return List.of("statistic", "playerdata");
            }
            case 3 -> {
                if (args[1].equals("statistic") | args[1].equals("stat")) {
                    List<String> list = new ArrayList<>(Statistic.values().length);
                    for (Statistic stat : Statistic.values()) {
                        if (!stat.isSubstatistic()) list.add(stat.name().toLowerCase());
                    }
                    return list;
                }
                return List.of("balance", "level", "xp", "killstreak", "bounty");
            }
            case 4 -> {
                return List.of("set", "add", "take", "multiply", "divide", "power", "squareroot");
            }
        }
        return Collections.emptyList();
    }
}
