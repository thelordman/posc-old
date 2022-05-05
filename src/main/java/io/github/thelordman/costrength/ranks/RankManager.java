package io.github.thelordman.costrength.ranks;

import io.github.thelordman.costrength.economy.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

    public static ChatColor getPlayerColor(OfflinePlayer player) {
        if (!(Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player) == null)) {
        return Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player).getColor();
        } else return ChatColor.GRAY;
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

    public static String levelPrefix(OfflinePlayer player) {
        int i = EconomyManager.getLevel(player.getUniqueId());
        String s = "&8[&7" + i + "&8]";
        if (i >= 1) s = "&8[&7" + i + "&8]";
        if (i >= 10) s = "&7[&f" + i + "&7]";
        if (i >= 25) s = "&f[&a" + i + "&f]";
        if (i >= 50) s = "&f[&e" + i + "&f]";
        if (i >= 75) s = "&f[&6" + i + "&f]";
        if (i >= 100) s = "&f[&4" + i + "&f]";

        return s;
    }
}