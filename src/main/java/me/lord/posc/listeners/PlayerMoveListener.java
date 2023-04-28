package me.lord.posc.listeners;

import me.lord.posc.Posc;
import me.lord.posc.data.DataManager;
import me.lord.posc.event.Event;
import me.lord.posc.event.ParkourEvent;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (event.hasChangedBlock()) {
            if (Event.getCurrent() instanceof ParkourEvent) {
                Event.Data eventData = DataManager.getPlayerData(player).getEventData();
                if (eventData instanceof ParkourEvent.Data parkourData) {
                    Location location = player.getLocation();
                    location = new Location(Posc.parkourWorld, location.x(), location.y() - 1, location.z());
                    ParkourEvent.Checkpoint checkpoint = ParkourEvent.Checkpoint.fromLocation(location);
                    if (checkpoint != null) {
                        if (parkourData.getCheckpoint() != checkpoint) {
                            parkourData.setCheckpoint(checkpoint);
                            player.sendMessage(TextUtil.c("&6Checkpoint Reached"));
                        }
                    }
                }
            }
        }
    }
}
