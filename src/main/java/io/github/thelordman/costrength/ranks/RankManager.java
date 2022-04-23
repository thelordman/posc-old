package io.github.thelordman.costrength.ranks;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.Team;

public class RankManager {
    public static void setRank(OfflinePlayer player, Team team) {
        team.addPlayer(player);
    }

    public static String getPrefix(OfflinePlayer player) {
        if (!(Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player) == null)) {
            return Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player).getPrefix();
        }
        else return "";
    }

    public static byte permissionLevel(OfflinePlayer player) {
        if (Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player) == null) return 0;
        return switch (Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player).getName()) {
            case "helper" -> 1;
            case "jrmod" -> 2;
            case "builder" -> 3;
            case "mod" -> 4;
            case "srmod" -> 5;
            case "developer" -> 6;
            case "admin" -> 7;
            case "headdeveloper" -> 8;
            case "owner" -> 9;
            default -> 0;
        };
    }

    public static boolean hasPermission(OfflinePlayer player, byte level) {
        return permissionLevel(player) >= level;
    }
}