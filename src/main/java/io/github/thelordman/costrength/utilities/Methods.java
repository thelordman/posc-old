package io.github.thelordman.costrength.utilities;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.ranks.RankManager;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.text.DecimalFormat;
import java.util.Arrays;

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
        String x = rStr((float) location.getX());
        String y = rStr((float) location.getY());
        String z = rStr((float) location.getZ());
        String yaw = rStr(location.getYaw());
        String pitch = rStr(location.getPitch());
        return extra ? location.getWorld().getName() + "," + x + "," + y + "," + z + "," + yaw + "," + pitch : "X: " + x + ", Y: " + y + ", Z: " + z;
    }

    public static Float getKdr(OfflinePlayer player) {
        return !(player.getStatistic(Statistic.PLAYER_KILLS) == 0) | !(player.getStatistic(Statistic.DEATHS) == 0) ?
                ((float) player.getStatistic(Statistic.PLAYER_KILLS)) / (float) player.getStatistic(Statistic.DEATHS) : 0f;
    }

    public static Float getBlocks(OfflinePlayer player) {
        return (float) player.getStatistic(Statistic.USE_ITEM, Material.IRON_PICKAXE) + player.getStatistic(Statistic.USE_ITEM, Material.DIAMOND_PICKAXE) + player.getStatistic(Statistic.USE_ITEM, Material.NETHERITE_PICKAXE) + player.getStatistic(Statistic.USE_ITEM, Material.GOLDEN_PICKAXE);
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
                player.getPlayer().sendMessage(Methods.cStr("&cInsufficient permissions.\n&6Please contact an admin or developer of &fCoStrength &6if you believe this shouldn't happen."));
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean inSpawn(Player player) {
        return player.getLocation().getX() < 13 && player.getLocation().getY() > -13;
    }

    public static boolean inCombat(Player player) {
        return Data.combatTag.containsKey(player);
    }

    public static void addPlayer(Player player, byte seconds) {
        Data.combatTag.put(player, seconds);
        new BukkitRunnable() {
            @Override
            public void run() {
                Data.combatTag.put(player, (byte) (Data.combatTag.get(player) - 1));
                player.sendActionBar(Methods.cStr("&cCombat tag: &6" + Data.combatTag.get(player) + " seconds"));
                if (Data.combatTag.get(player) <= 0) {
                    removePlayer(player);
                    cancel();
                }
            }
        }.runTaskTimer(CoStrength.instance, 0, 20);
    }

    public static void removePlayer(Player player) {
        Data.combatTag.remove(player);
        player.sendActionBar(Methods.cStr("&cCombat tag &6over"));
        player.sendMessage(Methods.cStr("&cYou are no longer combat tagged."));
    }

    public static boolean errorMessage(String error, Player player) {
        switch (error) {
            case "insufficientFunds" -> player.sendMessage(Methods.cStr("&cYou don't have enough money for that."));
            case "requires100" -> player.sendMessage(Methods.cStr("&cAmount must be at least $100."));
            case "notaNumber" -> player.sendMessage(Methods.cStr("&cAmount must be a number."));
        }
        return true;
    }

    public static void updateDisplayName(Player player) {
        String mid = RankManager.getPrefix(player).isEmpty() ? "" : "&8| ";
        player.setDisplayName(Methods.cStr(RankManager.levelPrefix(player) + " " + RankManager.getPrefix(player) + mid + RankManager.getPlayerColor(player) + player.getName() + "&r"));
        player.setPlayerListName(player.getDisplayName());
    }
}