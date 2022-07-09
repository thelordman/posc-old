package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.utilities.Methods;
import io.github.thelordman.costrength.utilities.data.Rank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class LeaderboardCommand implements TabExecutor {
    public static List<String> completions = List.of("bal", "level", "block", "kill", "death", "kdr", "playtime", "bounty", "killstreak");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Methods.cStr("&cOnly players can execute this command."));
            return true;
        }
        if (args.length == 0) return false;
        if (!completions.contains(args[0])) return false;

        StringBuilder builder = new StringBuilder();
        String type = switch (args[0]) {
            case "bal" -> "Balance";
            case "kdr" -> "K/D ratio";
            default -> Methods.rfStr(args[0]);
        };
        builder.append("\n&r    &6&l").append(type).append(" Leaderboard\n&r");

        List<Pair<Double, UUID>> pairs = new LinkedList<>();
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            double amount = switch (args[0]) {
                case "bal" -> EconomyManager.getBalance(player.getUniqueId());
                case "level" -> EconomyManager.getLevel(player.getUniqueId());
                case "block" -> EconomyManager.getBlocks(player);
                case "kill" -> EconomyManager.getKills(player);
                case "death" -> EconomyManager.getDeaths(player);
                case "kdr" -> Methods.getKdr(player);
                case "playtime" -> player.getStatistic(Statistic.PLAY_ONE_MINUTE);
                case "bounty" -> EconomyManager.getBounty(player.getUniqueId());
                case "killstreak" -> EconomyManager.getKillstreak(player.getUniqueId());
                default -> 0d;
            };
            pairs.add(Pair.with(amount, player.getUniqueId()));
        }

        pairs.sort(Comparator.reverseOrder());

        String a = args[0].equals("bal") | args[0].equals("bounty") ? "$" : "";
        int i = 1;
        for (Pair<Double, UUID> pair : pairs) {
            String mid = Rank.getRank(pair.getValue1()) == Rank.DEFAULT ? "&7" : " &8| " + Rank.getRank(pair.getValue1()).color;
            builder.append("\n &6").append(i).append(". ").append(Methods.levelPrefix(pair.getValue1())).append(" ")
                    .append(Rank.getRank(pair.getValue1()).name).append(mid).append(Bukkit.getOfflinePlayer(pair.getValue1()).getName())
                    .append(" &7&l- &f").append(a).append(Methods.rStr(pair.getValue0()));
            if (i == 10) break;
            i++;
        }

        sender.sendMessage(Methods.cStr(builder.toString()));

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) return StringUtil.copyPartialMatches(args[0], LeaderboardCommand.completions, new ArrayList<>());

        return Collections.emptyList();
    }
}