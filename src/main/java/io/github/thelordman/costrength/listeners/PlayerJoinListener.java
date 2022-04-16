package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String message = event.getPlayer().hasPlayedBefore() ? "&7[&a+&7] &7" + event.getPlayer().getDisplayName() : "&7[&a+&7] &7" + event.getPlayer().getDisplayName() + " &6#" + Bukkit.getServer().getOfflinePlayers().length;
        event.setJoinMessage(Methods.cStr(message));

        if (EconomyManager.getBalance(event.getPlayer()) == null) {
            EconomyManager.setBalance(event.getPlayer(), (float) 0);
        }
        if (EconomyManager.getKillstreak(event.getPlayer()) == null) {
            EconomyManager.setKillstreak(event.getPlayer(), (float) 0);
        }

        ScoreboardHandler.updateBoard(event.getPlayer());}
}
