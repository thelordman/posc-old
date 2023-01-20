package me.lord.posc.listeners;

import me.lord.posc.discord.events.PlayerAdvancement;
import me.lord.posc.utilities.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class PlayerAdvancementDoneListener implements Event {
    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {
        PlayerAdvancement.exe(event);
    }
}
