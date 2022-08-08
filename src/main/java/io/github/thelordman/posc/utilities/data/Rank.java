package io.github.thelordman.posc.utilities.data;

import io.github.thelordman.posc.utilities.Methods;
import org.bukkit.ChatColor;

import java.util.UUID;

public enum Rank {
    DEFAULT(0, 0, "", ChatColor.GRAY),
    VIP(0, 1, Methods.cStr("&aVIP"), ChatColor.GREEN),
    MVP(0, 2, Methods.cStr("&eMVP"), ChatColor.YELLOW),
    ELITE(0, 3, Methods.cStr("&3ELITE"), ChatColor.DARK_AQUA),
    LEGEND(0, 4, Methods.cStr("&6LEGEND"), ChatColor.GOLD),
    LOLI_LOVER(0, 5, Methods.cStr("&dLoli Lover"), ChatColor.LIGHT_PURPLE),
    TRIAL_DEVELOPER(2, 4, Methods.cStr("&dTrial Developer"), ChatColor.LIGHT_PURPLE),
    JRMOD(3, 4, Methods.cStr("&aJr. Mod"), ChatColor.GREEN),
    BUILDER(4, 4, Methods.cStr("&eBuilder"), ChatColor.YELLOW),
    MOD(5, 4, Methods.cStr("&eMod"), ChatColor.YELLOW),
    SRMOD(6, 4, Methods.cStr("&6Sr. Mod"), ChatColor.GOLD),
    DEVELOPER(7, 4, Methods.cStr("&dDeveloper"), ChatColor.LIGHT_PURPLE),
    HEAD_BUILDER(8, 4, Methods.cStr("&6Head Builder"), ChatColor.GOLD),
    ADMIN(9, 4, Methods.cStr("&cAdmin"), ChatColor.RED),
    HEAD_DEVELOPER(10, 4, Methods.cStr("&5Head Developer"), ChatColor.DARK_PURPLE),
    OWNER(11, 4, Methods.cStr("&4Owner"), ChatColor.RED);

    public final int permissionLevel;
    public final int donatorLevel;
    public final String name;
    public final ChatColor color;

    Rank(int permissionLevel, int donatorLevel, String name, ChatColor color) {
        this.permissionLevel = permissionLevel;
        this.donatorLevel = donatorLevel;
        this.name = name;
        this.color = color;
    }

    public static Rank getRank(UUID uuid) {
        return DataManager.getPlayerData(uuid).getRank();
    }
}