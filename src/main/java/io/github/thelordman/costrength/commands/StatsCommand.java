package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player ? ((Player) sender).getPlayer() : null;

        if (args.length == 0) {
            if (player == null) return false;
            sender.sendMessage(Methods.cStr("\n&r    &6&lYour Statistics\n&r\n" +
                    "&6Balance&7: &f$" + Methods.rStr(EconomyManager.getBalance(player)) + "\n" +
                    "&6Kills&7: &f" + Methods.rStr((float) player.getStatistic(Statistic.PLAYER_KILLS)) + "\n" +
                    "&6Deaths&7: &f" + Methods.rStr((float) player.getStatistic(Statistic.DEATHS)) + "\n" +
                    "&6K/D ratio&7: &f" + Methods.rStr(Methods.getKdr(player)) + "\n" +
                    "&6Killstreak&7: &f" + Methods.rStr(Float.valueOf(EconomyManager.getKillstreak(player))) + "\n&r" +
                    "&6Playtime&7: &f" + Methods.hourTimeFormat(player.getStatistic(Statistic.PLAY_ONE_MINUTE), 20) + "\n&r"));
        }
        else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) return false;
            sender.sendMessage(Methods.cStr("\n&r    &6&l" + target.getDisplayName() + "&6&l's Statistics\n&r\n" +
                    "&6Balance&7: &f$" + Methods.rStr(EconomyManager.getBalance(target)) + "\n" +
                    "&6Kills&7: &f" + Methods.rStr((float) target.getStatistic(Statistic.PLAYER_KILLS)) + "\n" +
                    "&6Deaths&7: &f" + Methods.rStr((float) target.getStatistic(Statistic.DEATHS)) + "\n" +
                    "&6K/D ratio&7: &f" + Methods.rStr(Methods.getKdr(target)) + "\n" +
                    "&6Killstreak&7: &f" + Methods.rStr(Float.valueOf(EconomyManager.getKillstreak(target))) + "\n&r" +
                    "&6Playtime&7: &f" + Methods.hourTimeFormat(target.getStatistic(Statistic.PLAY_ONE_MINUTE), 20) + "\n&r"));
        }
        return true;
    }
}
