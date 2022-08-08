package io.github.thelordman.posc.utilities;

import io.github.thelordman.posc.economy.EconomyManager;
import io.github.thelordman.posc.utilities.data.Data;
import io.github.thelordman.posc.utilities.data.Rank;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.UUID;

public class Methods {
    public static ChatColor playerChatColor(Player player, byte type) {
        ChatColor color;

        if (type == 0) {
            color = hasDonatorPermission(player.getUniqueId(), 1) ? ChatColor.WHITE : ChatColor.GRAY;
        }
        else {
            color = hasDonatorPermission(player.getUniqueId(), 1) ? ChatColor.GRAY : ChatColor.DARK_GRAY;
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
        return !(player.getStatistic(Statistic.PLAYER_KILLS) == 0) && !(player.getStatistic(Statistic.DEATHS) == 0)
                ? ((double) player.getStatistic(Statistic.PLAYER_KILLS)) / (double) player.getStatistic(Statistic.DEATHS)
                : (double) player.getStatistic(Statistic.PLAYER_KILLS);
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
        player.teleportAsync(new Location(Bukkit.getWorld("world"), 0.5, -41, 1.5), cause);
    }

    public static String replaceColorCodes(String string, char type) {
        return string.replace(type + "4", "").replace(type + "c", "").replace(type + "6", "").replace(type + "e", "").replace(type + "2", "").replace(type + "a", "").replace(type + "b", "").replace(type + "3", "").replace(type + "1", "").replace(type + "9", "").replace(type + "d", "").replace(type + "5", "").replace(type + "f", "").replace(type + "7", "").replace(type + "8", "").replace(type + "0", "")
                .replace(type + "k", "").replace(type + "l", "").replace(type + "m", "").replace(type + "n", "").replace(type + "o", "").replace(type + "r", "");
    }

    public static boolean hasPermission(UUID uuid, int level) {
        return Rank.getRank(uuid).permissionLevel >= level;
    }

    public static boolean hasDonatorPermission(UUID uuid, int level) {
        return Rank.getRank(uuid).donatorLevel >= level;
    }

    public static boolean checkCommandPermission(CommandSender sender, int level) {
        if (sender instanceof Player player) {
            if (!(hasPermission(player.getUniqueId(), level) | player.isOp())) {
                player.getPlayer().sendMessage(cStr("&cInsufficient permissions.\n&6Please contact an admin or developer of &fCoStrength &6if you believe this shouldn't happen."));
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean checkDonatorCommandPermission(CommandSender sender, int level) {
        if (sender instanceof Player player) {
            if (!(hasDonatorPermission(player.getUniqueId(), level) | player.isOp())) {
                player.getPlayer().sendMessage(cStr("&cInsufficient permissions.\n&cPlease contact an admin or developer of &fCoStrength &6if you believe this shouldn't happen."));
                return true;
            }
            return false;
        }
        return true;
    }

    public static boolean inSpawn(Location location) {
        return location.getX() < 13 && location.getZ() > -13;
    }

    public static boolean inCombat(Player player) {
        if (!Data.combatTag.containsKey(player)) return false;
        if (Data.combatTag.get(player) > System.currentTimeMillis() - 20000L) return true;
        Data.combatTag.remove(player);
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

    public static String levelPrefix(UUID uuid) {
        int i = EconomyManager.getLevel(uuid);
        String s = "&8[&7" + i + "&8]";
        if (i >= 1) s = "&8[&7" + i + "&8]";
        if (i >= 10) s = "&7[&f" + i + "&7]";
        if (i >= 25) s = "&f[&a" + i + "&f]";
        if (i >= 50) s = "&f[&e" + i + "&f]";
        if (i >= 75) s = "&f[&6" + i + "&f]";
        if (i >= 100) s = "&f[&4" + i + "&f]";

        return s;
    }

    public static void updateDisplayName(Player player) {
        Rank rank = Rank.getRank(player.getUniqueId());
        String mid = rank == Rank.DEFAULT ? "" : " &8| ";
        player.setDisplayName(Methods.cStr(levelPrefix(player.getUniqueId()) + " " + rank.name + mid
                + rank.color + player.getName() + "&r"));
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

    public static String getMaterialName(Material material) {
        String name = material.name();
        StringBuilder builder = new StringBuilder();
        for (String string : name.split("_")) {
            builder.append(rfStr(string)).append(" ");
        }
        builder.deleteCharAt(builder.lastIndexOf(" "));
        return builder.toString();
    }

    public static boolean doesRankExist(String rank) {
        for (Rank rnk : Rank.values()) {
            if (rnk.name().equals(rank))
                return true;
        }
        return false;
    }
}