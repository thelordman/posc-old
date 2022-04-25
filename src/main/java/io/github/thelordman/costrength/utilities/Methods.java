package io.github.thelordman.costrength.utilities;

import io.github.thelordman.costrength.ranks.RankManager;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scoreboard.Team;

import java.text.DecimalFormat;
import java.util.Arrays;

import static java.lang.Math.round;

public class Methods {
    public static ChatColor playerChatColor(Player player, Byte type) {
        ChatColor color;

        if (type == 0) {
            color = RankManager.hasPermission(Bukkit.getOfflinePlayer(player.getName()), (byte) 1) ? ChatColor.WHITE : ChatColor.GRAY;
        }
        else {
            color = RankManager.hasPermission(Bukkit.getOfflinePlayer(player.getName()), (byte) 1) ? ChatColor.GRAY : ChatColor.DARK_GRAY;
        }

        return color;
    }

    public static String locToString(Location location, Boolean extra) {
        if (extra) {
            return location.getWorld().getName() + "," + round(location.getX()) + "," + round(location.getY()) + "," + round(location.getZ()) +
                    "," + round(location.getYaw()) + "," + round(location.getPitch());
        } else {
            return "X: " + round(location.getX()) + ", Y: " + round(location.getY()) + ", Z: " + round(location.getZ());
        }
    }

    public static Float getKdr(Player player) {
        return !(player.getStatistic(Statistic.PLAYER_KILLS) == 0) | !(player.getStatistic(Statistic.DEATHS) == 0) ?
                ((float) player.getStatistic(Statistic.PLAYER_KILLS)) / (float) player.getStatistic(Statistic.DEATHS) : 0f;
    }
    public static String cStr(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String rfStr(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static String rStr(Float number) {
        String pattern = "###,###.##";
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(number);
    }

    public static String arrayToString(String[] array) {
        return Arrays.toString(array).replace("[", "").replace("]", "").replace(",", "");
    }

    public static void teleportPlayerToSpawn(Player player, PlayerTeleportEvent.TeleportCause cause) {
        player.teleportAsync(new Location(Bukkit.getWorld("world"), 0.5, -60, 0.5, 0, 0), cause);
    }

    public static String hourTimeFormat(Integer seconds, Integer division) {
        int hours = (seconds / division) / 3600;

        return hours + " hours";
    }

    public static Team getTeamFromString(String teamString) {
        return Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamString);
    }

    public static String replaceColorCodes(String string, char type) {
        return string.replace(type + "4", "").replace(type + "c", "").replace(type + "6", "").replace(type + "e", "").replace(type + "2", "").replace(type + "a", "").replace(type + "b", "").replace(type + "3", "").replace(type + "1", "").replace(type + "9", "").replace(type + "d", "").replace(type + "5", "").replace(type + "f", "").replace(type + "7", "").replace(type + "8", "").replace(type + "0", "")
                .replace(type + "k", "").replace(type + "l", "").replace(type + "m", "").replace(type + "n", "").replace(type + "o", "").replace(type + "r", "");
    }

    public static boolean checkCommandPermission(CommandSender sender, byte level) {
        if (sender instanceof Player) {
            OfflinePlayer player = ((OfflinePlayer) sender);
            if (!(RankManager.hasPermission(player, level) | player.isOp())) {
                player.getPlayer().sendMessage(Methods.cStr("&cInsufficient permissions.\n&6Please contact an admin or developer of &fCoStrength &6if you believe this shouldn't happen."));
                return false;
            }
            return true;
        }
        return false;
    }
}