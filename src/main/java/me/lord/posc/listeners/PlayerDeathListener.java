package me.lord.posc.listeners;

import me.lord.posc.discord.events.PlayerDeath;
import me.lord.posc.utilities.Event;
import me.lord.posc.utilities.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Event {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Component original = event.deathMessage();
        event.deathMessage(Component.text(""));
        Bukkit.broadcast(TextUtil.c("&cDeath &7| &f").append(event.deathMessage().replaceText(TextReplacementConfig.builder()
                .match(event.getPlayer().getName())
                .replacement(TextUtil.c("&e" + event.getPlayer().getName() + "&f"))
                .build())));

        event.deathMessage(original);
        PlayerDeath.exe(event);
    }
}
