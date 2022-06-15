package io.github.thelordman.costrength.utilities;

import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.ranks.RankManager;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scoreboard.Team;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Methods {
    public static ChatColor playerChatColor(Player player, byte type) {
        ChatColor color;

        if (type == 0) {
            color = RankManager.hasDonatorPermission(player, (byte) 1) ? ChatColor.WHITE : ChatColor.GRAY;
        }
        else {
            color = RankManager.hasDonatorPermission(player, (byte) 1) ? ChatColor.GRAY : ChatColor.DARK_GRAY;
        }

        return color;
    }

    public static String locToString(Location location, Boolean extra) {
        String x = rStr(location.getX());
        String y = rStr(location.getY());
        String z = rStr(location.getZ());
        String yaw = rStr((double) location.getYaw());
        String pitch = rStr((double) location.getPitch());
        return extra ? location.getWorld().getName() + "," + x + "," + y + "," + z + "," + yaw + "," + pitch : "X: " + x + ", Y: " + y + ", Z: " + z;
    }

    public static Double getKdr(OfflinePlayer player) {
        return !(player.getStatistic(Statistic.PLAYER_KILLS) == 0) | !(player.getStatistic(Statistic.DEATHS) == 0)
                ? ((double) player.getStatistic(Statistic.PLAYER_KILLS)) / (double) player.getStatistic(Statistic.DEATHS)
                : (double) player.getStatistic(Statistic.PLAYER_KILLS);
    }

    public static Double getBlocks(OfflinePlayer player) {
        return (double) player.getStatistic(Statistic.USE_ITEM, Material.IRON_PICKAXE) + player.getStatistic(Statistic.USE_ITEM, Material.DIAMOND_PICKAXE) + player.getStatistic(Statistic.USE_ITEM, Material.NETHERITE_PICKAXE) + player.getStatistic(Statistic.USE_ITEM, Material.GOLDEN_PICKAXE);
    }

    public static String cStr(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String rfStr(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    public static String rStr(Double number) {
        return new DecimalFormat("###,###.##").format(number);
    }

    public static String arrayToString(String[] array) {
        return Arrays.toString(array).replace("[", "").replace("]", "").replace(",", "");
    }

    public static void teleportPlayerToSpawn(Player player, PlayerTeleportEvent.TeleportCause cause) {
        player.teleportAsync(Bukkit.getWorld("world").getSpawnLocation(), cause);
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
                player.getPlayer().sendMessage(cStr("&cInsufficient permissions.\n&6Please contact an admin or developer of &fCoStrength &6if you believe this shouldn't happen."));
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean checkDonatorCommandPermission(CommandSender sender, byte level) {
        if (sender instanceof Player) {
            OfflinePlayer player = ((OfflinePlayer) sender);
            if (!(RankManager.hasDonatorPermission(player, level) | player.isOp())) {
                player.getPlayer().sendMessage(cStr("&cInsufficient permissions.\n&cPlease contact an admin or developer of &fCoStrength &6if you believe this shouldn't happen."));
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean inSpawn(Player player) {
        return player.getLocation().getX() < 13 && player.getLocation().getZ() > -13;
    }

    public static boolean inCombat(Player player) {
        if (!Data.combatTag.containsKey(player)) return false;
        if (Data.combatTag.get(player) < System.currentTimeMillis() - 20000L) {
            Data.combatTag.remove(player);
            return true;
        }
        return false;
    }

    public static boolean errorMessage(String error, Player player) {
        switch (error) {
            case "insufficientFunds" -> player.sendMessage(cStr("&cYou don't have enough money for that."));
            case "requires100" -> player.sendMessage(cStr("&cAmount must be at least $100."));
            case "notaNumber" -> player.sendMessage(cStr("&cAmount must be a number."));
        }
        return true;
    }

    public static void updateDisplayName(Player player) {
        String mid = RankManager.getPrefix(player).isEmpty() ? "" : "&8| ";
        player.setDisplayName(Methods.cStr(RankManager.levelPrefix(player) + " " + RankManager.getPrefix(player) + mid + RankManager.getPlayerColor(player) + player.getName() + "&r"));
        player.setDisplayName(player.getDisplayName().replace("fastskating", "fatskating"));
        if (EconomyManager.getBounty(player.getUniqueId()) != 0) player.setPlayerListName(player.getDisplayName() + cStr(" &6[&f$" + rStr(EconomyManager.getBounty(player.getUniqueId())) + "&6]"));
        else player.setPlayerListName(player.getDisplayName());
        player.setCustomName(player.getDisplayName());
    }

    public static String toRomanNumeral(byte input) {
        return switch (input) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            case 6 -> "VI";
            case 7 -> "VII";
            case 8 -> "VIII";
            case 9 -> "IX";
            case 10 -> "X";
            default -> null;
        };
    }
}