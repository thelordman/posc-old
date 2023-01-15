package me.lord.posc.economy;

import me.lord.posc.data.DataManager;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Economy {
    public static Account getAccount(UUID uuid) {
        return DataManager.getPlayerData(uuid).getAccount();
    }

    public static Account getAccount(Player player) {
        return DataManager.getPlayerData(player).getAccount();
    }

    public static void createAccount(Player player) {
        DataManager.getPlayerData(player).setAccount(new Account());
    }

    public static void deleteAccount(Player player) {
        DataManager.getPlayerData(player).setAccount(null);
    }
}
