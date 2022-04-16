package io.github.thelordman.costrength.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Methods {
    public static ChatColor playerChatColor(Player player, String type) {
        ChatColor color;

        if (type.equals("primary")) {
            color = (player.hasPermission("chatcolor.white")) ? ChatColor.WHITE : ChatColor.GRAY;
        }
        else {
            color = (player.hasPermission("chatcolor.white")) ? ChatColor.GRAY : ChatColor.DARK_GRAY;
        }

        return color;
    }

    public static Float getKdr(Player player) {

        return !(player.getStatistic(Statistic.PLAYER_KILLS) == 0) | !(player.getStatistic(Statistic.DEATHS) == 0) ?
                ((float) player.getStatistic(Statistic.PLAYER_KILLS)) / (float) player.getStatistic(Statistic.DEATHS) : 0;
    }
    public static String cStr(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static Player determineTarget(CommandSender sender, String target) {
        if (sender.toString().equals(target)) return (Player) sender;
        return Bukkit.getPlayerExact(target);
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
        player.teleportAsync(new Location(Bukkit.getWorld("world"), 0, -60, 0, 0, 0), cause);
    }
}