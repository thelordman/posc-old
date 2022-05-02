package io.github.thelordman.costrength.mining;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MineHandler {
    public static void resetMine(byte type, Player player) {
        String executor = player == null ? "Console" : player.getDisplayName();
        String broadcast = type == 0 ? "\n&6&lCoStrength &8| &fMine is being refilled by " + executor + "&f.\n" : "\n&6&lCoStrength &8| &fA beacon has been found by " + executor + "&f.\n&6&lCoStrength &8| &fMine is being refilled.\n";
        Bukkit.broadcastMessage(Methods.cStr(broadcast));

        //mine1
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/world world");
        Bukkit.getScheduler().runTaskLater(CoStrength.get(), () -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/pos1 14,-42,-14"), 10L);
        Bukkit.getScheduler().runTaskLater(CoStrength.get(), () -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/pos2 70,-63,-70"), 20L);
        Bukkit.getScheduler().runTaskLater(CoStrength.get(), () -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/set 75%stone,15%coal_ore,5%iron_ore,3.5%lapis_ore,1%diamond_ore,0.49%emerald_ore,0.01%beacon"), 30L);

        //mine2
        Bukkit.getScheduler().runTaskLater(CoStrength.get(), () -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/pos1 14,-42,12"), 60L);
        Bukkit.getScheduler().runTaskLater(CoStrength.get(), () -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/pos2 70,-63,-13"), 70L);
        Bukkit.getScheduler().runTaskLater(CoStrength.get(), () -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/set 75%stone,15%coal_ore,5%iron_ore,3.5%lapis_ore,1%diamond_ore,0.49%emerald_ore,0.01%beacon"), 80L);

        //mine3
        Bukkit.getScheduler().runTaskLater(CoStrength.get(), () -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/pos1 -12,-42,-14"), 110L);
        Bukkit.getScheduler().runTaskLater(CoStrength.get(), () -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/pos2 13,-63,-70"), 120L);
        Bukkit.getScheduler().runTaskLater(CoStrength.get(), () -> Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/set 75%stone,15%coal_ore,5%iron_ore,3.5%lapis_ore,1%diamond_ore,0.49%emerald_ore,0.01%beacon"), 130L);
    }
}