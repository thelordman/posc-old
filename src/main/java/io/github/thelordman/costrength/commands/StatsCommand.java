package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.economy.LevelHandler;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatsCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        OfflinePlayer player = sender instanceof Player ? ((Player) sender).getPlayer() : null;

        if (args.length == 0) {
            sender.sendMessage(Methods.cStr("\n&r    &6&lYour Statistics\n&r\n" +
                    " &6Balance&7: &f$" + Methods.rStr(EconomyManager.getBalance(player.getUniqueId())) + "\n" +
                    " &6Blocks&7: &f" + Methods.rStr(Methods.getBlocks(player)) + "\n" +
                    " &6Level&7: &f" + Methods.rStr((double) EconomyManager.getLevel(player.getUniqueId())) + "\n" +
                    " &6XP&7: &f" + Methods.rStr(EconomyManager.getXp(player.getUniqueId())) + "&7/&f" + Methods.rStr(LevelHandler.xpRequirement(player.getUniqueId())) + "\n" +
                    " &6Bounty&7: &f$" + Methods.rStr(EconomyManager.getBounty(player.getUniqueId())) + "\n" +
                    " &6Kills&7: &f" + Methods.rStr((double) player.getStatistic(Statistic.PLAYER_KILLS)) + "\n" +
                    " &6Deaths&7: &f" + Methods.rStr((double) player.getStatistic(Statistic.DEATHS)) + "\n" +
                    " &6K/D ratio&7: &f" + Methods.rStr(Methods.getKdr(player)) + "\n" +
                    " &6Killstreak&7: &f" + Methods.rStr((double) EconomyManager.getKillstreak(player.getUniqueId())) + "\n" +
                    " &6Playtime&7: &f" + Methods.hourTimeFormat(player.getStatistic(Statistic.PLAY_ONE_MINUTE), 20) + "\n&r"));
        }
        else {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            sender.sendMessage(Methods.cStr("\n&r    &6&l" + target.getName() + "&6&l's Statistics\n&r\n" +
                    " &6Balance&7: &f$" + Methods.rStr(EconomyManager.getBalance(target.getUniqueId())) + "\n" +
                    " &6Blocks&7: &f" + Methods.rStr(Methods.getBlocks(target)) + "\n" +
                    " &6Level&7: &f" + Methods.rStr((double) EconomyManager.getLevel(target.getUniqueId())) + "\n" +
                    " &6XP&7: &f" + Methods.rStr(EconomyManager.getXp(target.getUniqueId())) + "&7/&f" + Methods.rStr(LevelHandler.xpRequirement(target.getUniqueId())) + "\n" +
                    " &6Bounty&7: &f$" + Methods.rStr(EconomyManager.getBounty(player.getUniqueId())) + "\n" +
                    " &6Kills&7: &f" + Methods.rStr((double) target.getStatistic(Statistic.PLAYER_KILLS)) + "\n" +
                    " &6Deaths&7: &f" + Methods.rStr((double) target.getStatistic(Statistic.DEATHS)) + "\n" +
                    " &6K/D ratio&7: &f" + Methods.rStr(Methods.getKdr(target)) + "\n" +
                    " &6Killstreak&7: &f" + Methods.rStr((double) EconomyManager.getKillstreak(target.getUniqueId())) + "\n" +
                    " &6Playtime&7: &f" + Methods.hourTimeFormat(target.getStatistic(Statistic.PLAY_ONE_MINUTE), 20) + "\n&r"));
        }
        return true;
    }
}
