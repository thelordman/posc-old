package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.date.Date;
import io.github.thelordman.posc.date.DateType;
import io.github.thelordman.posc.economy.EconomyManager;
import io.github.thelordman.posc.economy.LevelHandler;
import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@CommandName("stats")
public class StatsCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        OfflinePlayer target = args.length == 0 ? (OfflinePlayer) sender : Bukkit.getOfflinePlayer(args[0]);
        sender.sendMessage(Methods.cStr("\n&r    &6&l" + target.getName() + "&6&l's Statistics\n&r\n" +
                " &6Balance&7: &f$" + Methods.rStr(EconomyManager.getBalance(target.getUniqueId())) + "\n" +
                " &6Blocks&7: &f" + Methods.rStr(EconomyManager.getBlocks(target)) + "\n" +
                " &6Level&7: &f" + Methods.rStr((double) EconomyManager.getLevel(target.getUniqueId())) + "\n" +
                " &6XP&7: &f" + Methods.rStr(EconomyManager.getXp(target.getUniqueId())) + "&7/&f" + Methods.rStr(LevelHandler.xpRequirement(target.getUniqueId())) + "\n" +
                " &6Bounty&7: &f$" + Methods.rStr(EconomyManager.getBounty(target.getUniqueId())) + "\n" +
                " &6Kills&7: &f" + Methods.rStr((double) target.getStatistic(Statistic.PLAYER_KILLS)) + "\n" +
                " &6Deaths&7: &f" + Methods.rStr((double) target.getStatistic(Statistic.DEATHS)) + "\n" +
                " &6K/D ratio&7: &f" + Methods.rStr(Methods.getKdr(target)) + "\n" +
                " &6Killstreak&7: &f" + Methods.rStr((double) EconomyManager.getKillstreak(target.getUniqueId())) + "\n" +
                " &6Playtime&7: &f" + Date.dateTimeFormat(target.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20, DateType.HOUR_MINUTE_SECOND) + "\n&r"));
        return true;
    }
}