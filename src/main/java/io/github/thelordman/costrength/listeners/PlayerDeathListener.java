package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Player killer = event.getPlayer().getKiller();

        EconomyManager.killstreak.put(player.getUniqueId(), EconomyManager.killstreak.get(killer.getUniqueId()) + 1);

        if (player.hasPermission("chatcolor.white") | killer.hasPermission("chatcolor.white")) {
            event.setDeathMessage(Methods.cStr("&cDeath &8| &f" + event.getDeathMessage()));
        }
        else {
            event.setDeathMessage(Methods.cStr("&7Death &8| &7" + event.getDeathMessage()));
        }

        ScoreboardHandler.updateBoard(player);
        ScoreboardHandler.updateBoard(killer);
    }
}
