package io.github.thelordman.posc.scoreboard;

import io.github.thelordman.posc.economy.EconomyManager;
import io.github.thelordman.posc.utilities.Methods;
import io.github.thelordman.posc.utilities.data.Data;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class ScoreboardHandler {

    public static void updateBoard(Player player) {
        FastBoard scoreboard = Data.scoreboard.containsKey(player.getUniqueId()) ? Data.scoreboard.get(player.getUniqueId()) : new FastBoard(player);
        scoreboard.updateTitle(Methods.cStr("&6&lCoStrength &7(" + Bukkit.getOnlinePlayers().size() + "&7/" + Bukkit.getMaxPlayers() + "&7)"));
        scoreboard.updateLines(
                "",
                Methods.cStr("&6Balance&7: &f$" + Methods.rStr(EconomyManager.getBalance(player.getUniqueId()))),
                Methods.cStr("&6Blocks&7: &f" + Methods.rStr(EconomyManager.getBlocks(player))),
                Methods.cStr("&6Level&7: &f" + Methods.rStr((double) EconomyManager.getLevel(player.getUniqueId()))),
                Methods.cStr("&6Bounty&7: &f$" + Methods.rStr(EconomyManager.getBounty(player.getUniqueId()))),
                "",
                Methods.cStr("&6Kills&7: &f" + Methods.rStr((double) player.getStatistic(Statistic.PLAYER_KILLS))),
                Methods.cStr("&6Deaths&7: &f" + Methods.rStr((double) player.getStatistic(Statistic.DEATHS))),
                Methods.cStr("&6K/D ratio&7: &f" + Methods.rStr(Methods.getKdr(player))),
                Methods.cStr("&6Killstreak&7: &f" + Methods.rStr((double) EconomyManager.getKillstreak(player.getUniqueId()))),
                "",
                Methods.cStr("&f&nCoStrength.minehut.gg")
        );
        Data.scoreboard.put(player.getUniqueId(), scoreboard);
    }
}