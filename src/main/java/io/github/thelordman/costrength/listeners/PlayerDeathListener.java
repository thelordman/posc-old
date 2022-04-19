package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.utilities.Methods;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.ChatColor;
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
        String originalMsg = event.getDeathMessage();

        if (!(killer == null)) {
            killRewards(killer, player);

            if (killer.hasPermission("chatcolor.white")) event.setDeathMessage(Methods.cStr("&cDeath &8| &f" + event.getDeathMessage()));
            else event.setDeathMessage(Methods.cStr("&7Death &8| &7" + event.getDeathMessage()));
            ScoreboardHandler.updateBoard(killer);
        }
        else {
            player.sendTitle(ChatColor.RED + "You Died", "", 10, 100, 40);
            event.setDeathMessage(Methods.cStr("&7Death &8| &7" + event.getDeathMessage()));
        }
        EconomyManager.setKillstreak(player, 0);

        ScoreboardHandler.updateBoard(player);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getPlayer().getDisplayName() + " Died", null, "https://crafatar.com/avatars/" + event.getPlayer().getUniqueId());
        builder.setColor(Color.RED);
        if (event.getDeathMessage() != null) {
            builder.setDescription(originalMsg);
        }
        else {
            builder.setDescription("He just died");
        }

        CoStrength.minecraftChatChannel.sendMessageEmbeds(builder.build()).queue();
    }

    private void killRewards(Player killer, Player victim) {
        Integer killerKillstreak = EconomyManager.getKillstreak(killer);
        Float victimBalance = EconomyManager.getBalance(victim);

        EconomyManager.setKillstreak(killer, killerKillstreak + 1);
        Float takeFromVictim = victimBalance == 0f ? 0f : victimBalance / 100;
        Float reward = 1f + (killerKillstreak == 0f ? 0f : killerKillstreak / 5) + EconomyManager.getKillstreak(victim) + takeFromVictim;

        EconomyManager.setBalance(victim, victimBalance - takeFromVictim);
        EconomyManager.setBalance(killer, EconomyManager.getBalance(killer) + reward);

        victim.sendTitle(ChatColor.RED + "You Died", ChatColor.GOLD + killer.getDisplayName() + " stole " + ChatColor.WHITE + "$" + Methods.rStr(takeFromVictim), 10, 100, 40);
        killer.sendActionBar(victim.getDisplayName() + " &8| &f$" + Methods.rStr(reward));
    }
}
