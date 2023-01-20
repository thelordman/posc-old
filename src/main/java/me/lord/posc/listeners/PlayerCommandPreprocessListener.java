package me.lord.posc.listeners;

import me.lord.posc.discord.events.PlayerCommand;
import me.lord.posc.utilities.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessListener implements Event {
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        PlayerCommand.exe(event);
    }
}
