package io.github.thelordman.posc.utilities;

import io.github.thelordman.posc.utilities.data.DataManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public enum Rank {
    DEFAULT(0, 0, "", ChatColor.GRAY),
    VIP(0, 1, "&aVIP", ChatColor.GREEN),
    MVP(0, 2, "&eMVP", ChatColor.YELLOW),
    ELITE(0, 3, "&3ELITE", ChatColor.DARK_AQUA),
    LEGEND(0, 4, "&6LEGEND", ChatColor.GOLD),
    LOLI_LOVER(0, 5, "&dLoli Lover", ChatColor.LIGHT_PURPLE),
    TRIAL_DEVELOPER(2, 4, "&dTrial Developer", ChatColor.LIGHT_PURPLE),
    JRMOD(3, 4, "&aJr. Mod", ChatColor.GREEN),
    BUILDER(4, 4, "&eBuilder", ChatColor.YELLOW),
    MOD(5, 4, "&eMod", ChatColor.YELLOW),
    SRMOD(6, 4, "&6Sr. Mod", ChatColor.GOLD),
    DEVELOPER(7, 4, "&dDeveloper", ChatColor.LIGHT_PURPLE),
    HEAD_BUILDER(8, 4, "&6Head Builder", ChatColor.GOLD),
    ADMIN(9, 4, "&cAdmin", ChatColor.RED),
    HEAD_DEVELOPER(10, 4, "&5Head Developer", ChatColor.DARK_PURPLE),
    OWNER(11, 4, "&4Owner", ChatColor.DARK_RED);

    public final int permissionLevel;
    public final int donatorLevel;
    public final String name;
    public final ChatColor color;

    Rank(int permissionLevel, int donatorLevel, String name, ChatColor color) {
        this.permissionLevel = permissionLevel;
        this.donatorLevel = donatorLevel;
        this.name = Methods.cStr(name);
        this.color = color;
    }

    public static Rank getRank(UUID uuid) {
        return DataManager.getPlayerData(uuid).getRank();
    }

    public static boolean hasPermission(CommandSender sender, int permissionLevel) {
        if(sender instanceof ConsoleCommandSender)
            return true;
        if(sender instanceof Player player)
            return getRank(player.getUniqueId()).permissionLevel >= permissionLevel;
        return false;
    }

    public static boolean hasDonorPermission(CommandSender sender, int donatorLevel) {
        if(sender instanceof ConsoleCommandSender)
            return true;
        if(sender instanceof Player player)
            return getRank(player.getUniqueId()).donatorLevel >= donatorLevel;
        return false;
    }
}