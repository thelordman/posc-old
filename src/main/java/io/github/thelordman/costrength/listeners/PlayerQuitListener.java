package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.utilities.Methods;
import io.github.thelordman.costrength.scoreboard.FastBoard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(Methods.cStr("&7[&c-&7] &7" + event.getPlayer().getDisplayName()));

        FastBoard scoreboard = CoStrength.scoreboard.remove(event.getPlayer().getUniqueId());

        if (scoreboard != null) {
            scoreboard.delete();
        }
    }
}
