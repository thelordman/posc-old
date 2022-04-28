package io.github.thelordman.costrength.mine;

import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MineHandler {
    public static void resetMine(byte type, Player player) {
        float time = (float) System.currentTimeMillis();

        String executor = player == null ? "Console" : player.getDisplayName();
        String broadcast = type == 0 ? "\n&6&lCoStrength &8| &fMine is being refilled by " + executor + "&f." : "\n&6&lCoStrength &8| &fA beacon has been found by " + executor + "&f.\n&fMine is being refilled.";

        //replace air 75%stone,15%coal_ore,5%iron_ore,3.5%lapis_ore,1%diamond_ore,0.49%emerald_ore,0.01%beacon

        time = (System.currentTimeMillis() - time) / 1000;
        Bukkit.broadcastMessage(Methods.cStr(broadcast + "\n&r\n&6Mine was reset in &f" + Methods.rStr(time) + " &fseconds &6.\n"));
    }
}