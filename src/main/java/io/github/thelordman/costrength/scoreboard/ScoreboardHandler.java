package io.github.thelordman.costrength.scoreboard;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class ScoreboardHandler {
    public static void updateBoard(Player player) {
        FastBoard scoreboard = new FastBoard(player);
        scoreboard.updateTitle(Methods.cStr("&6&lCoStrength &7(" + Bukkit.getOnlinePlayers().size() + "&7/" + Bukkit.getMaxPlayers() + "&7)"));
        scoreboard.updateLines(
                "",
                Methods.cStr("&6Balance&7: &f$" + Methods.rStr(EconomyManager.getBalance(player))),
                Methods.cStr("&6Blocks&7: &f" + Methods.rStr((float) Methods.getBlocks(player))),
                "",
                Methods.cStr("&6Kills&7: &f" + Methods.rStr((float) player.getStatistic(Statistic.PLAYER_KILLS))),
                Methods.cStr("&6Deaths&7: &f" + Methods.rStr((float) player.getStatistic(Statistic.DEATHS))),
                Methods.cStr("&6K/D ratio&7: &f" + Methods.rStr(Methods.getKdr(player))),
                "",
                Methods.cStr("&f&nCoStrength.minehut.gg")
        );
        CoStrength.scoreboard.put(player.getUniqueId(), scoreboard);
    }
}
