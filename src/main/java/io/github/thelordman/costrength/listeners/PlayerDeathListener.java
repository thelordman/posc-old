package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.utilities.Methods;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.awt.*;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Player killer = event.getPlayer().getKiller();

        if (!(killer == null)) {
            EconomyManager.setKillstreak(killer, EconomyManager.getKillstreak(killer) + 1);
            Float takeFromVictim = EconomyManager.getKillstreak(player) == 0f ? 0f : EconomyManager.getBalance(player) / 100;
            Float reward = 1f + (EconomyManager.getKillstreak(killer) == 0f ? 0f : EconomyManager.getKillstreak(killer) / 5) + takeFromVictim;
            EconomyManager.setBalance(killer, EconomyManager.getBalance(killer) + reward);
            if (killer.hasPermission("chatcolor.white")) {
                event.setDeathMessage(Methods.cStr("&cDeath &8| &f" + event.getDeathMessage()));
            }
            else {
                event.setDeathMessage(Methods.cStr("&7Death &8| &7" + event.getDeathMessage()));
            }
            ScoreboardHandler.updateBoard(killer);
        }
        else {
            event.setDeathMessage(Methods.cStr("&7Death &8| &7" + event.getDeathMessage()));
        }
        EconomyManager.setKillstreak(player, 0);

        ScoreboardHandler.updateBoard(player);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getPlayer().getDisplayName() + " Died", null, "https://crafatar.com/avatars/" + event.getPlayer().getUniqueId());
        builder.setColor(Color.RED);
        if (event.getDeathMessage() != null) {
            builder.setDescription(event.getDeathMessage());
        }

        CoStrength.minecraftChatChannel.sendMessageEmbeds(builder.build()).queue();
    }
}
