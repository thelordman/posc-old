package me.lord.posc.listeners;

import me.lord.posc.data.DataManager;
import me.lord.posc.discord.events.PlayerDeath;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getPlayer();
        Player attacker = victim.getKiller();

        // GameRule SHOW_DEATH_MESSAGES must be set to false, or it will duplicate
        Bukkit.broadcast(TextUtil.c("&cDeath &7| &f").append(event.deathMessage()));

        DataManager.getPlayerData(victim).getScoreboard().updateDeaths();
        if (attacker != null) {
            DataManager.getPlayerData(attacker).getScoreboard().updateKills();
        }

        PlayerDeath.exe(event);
    }
}
