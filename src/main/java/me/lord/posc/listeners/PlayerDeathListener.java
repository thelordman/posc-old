package me.lord.posc.listeners;

import me.lord.posc.discord.events.PlayerDeath;
import me.lord.posc.utilities.Event;
import me.lord.posc.utilities.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Event {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Bukkit.broadcast(TextUtil.c("&cDeath &7| &f").append(event.deathMessage()));

        PlayerDeath.exe(event);
    }
}
