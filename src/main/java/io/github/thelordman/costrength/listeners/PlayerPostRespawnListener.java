package io.github.thelordman.costrength.listeners;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import io.github.thelordman.costrength.items.Kit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerPostRespawnListener implements Listener {
    @EventHandler
    public void onPlayerPostRespawn(PlayerPostRespawnEvent event) {
        Kit.joinKit(event.getPlayer());
    }
}
