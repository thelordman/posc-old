package io.github.thelordman.posc.listeners;

import io.github.thelordman.posc.utilities.Methods;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.isCancelled())
            return;
        for(Block block : event.blockList()) {
            if (Methods.inSpawn(block.getLocation())) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
