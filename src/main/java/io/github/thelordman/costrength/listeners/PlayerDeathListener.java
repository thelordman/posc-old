package io.github.thelordman.costrength.listeners;

import io.github.thelordman.costrength.commands.BountyCommand;
import io.github.thelordman.costrength.discord.Discord;
import io.github.thelordman.costrength.economy.EconomyManager;
import io.github.thelordman.costrength.economy.LevelHandler;
import io.github.thelordman.costrength.ranks.RankManager;
import io.github.thelordman.costrength.scoreboard.ScoreboardHandler;
import io.github.thelordman.costrength.utilities.Data;
import io.github.thelordman.costrength.utilities.Methods;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.awt.*;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getPlayer();
        Player killer = event.getPlayer().getKiller();
        String originalMsg = event.getDeathMessage();

        if (killer != null && killer != victim) {
            killRewards(killer, victim);

            if (RankManager.hasPermission(Bukkit.getOfflinePlayer(killer.getName()), (byte) 1)) event.setDeathMessage(Methods.cStr("&cDeath &8| &f" + event.getDeathMessage()));
            else event.setDeathMessage(Methods.cStr("&7Death &8| &7" + event.getDeathMessage()));

            if (Data.bounty.containsKey(victim)) {
                EconomyManager.setBalance(killer, EconomyManager.getBalance(killer) + Data.bounty.get(victim));
                Data.bounty.remove(victim);
                Bukkit.broadcastMessage(Methods.cStr(killer.getDisplayName() + " &6collected the bounty of &f$" + Data.bounty.get(victim) + " &6on " + victim.getDisplayName() + "&6."));
            }

            ScoreboardHandler.updateBoard(killer);
        }
        else {
            victim.sendTitle(ChatColor.RED + "You Died", "");
            event.setDeathMessage(Methods.cStr("&7Death &8| &7" + event.getDeathMessage()));
        }
        EconomyManager.setKillstreak(victim, 0);

        ScoreboardHandler.updateBoard(victim);
        
        //Discord
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Methods.replaceColorCodes(event.getPlayer().getDisplayName() + " Died", '§'), null, "https://crafatar.com/avatars/" + event.getPlayer().getUniqueId());
        builder.setColor(Color.RED);
        if (originalMsg != null) {
            builder.setDescription(Methods.replaceColorCodes(originalMsg, '&'));
        }
        else {
            builder.setDescription("He just died");
        }

        Discord.minecraftChatChannel.sendMessageEmbeds(builder.build()).queue();
    }

    private void killRewards(Player killer, Player victim) {
        //Victim
        Integer killerKillstreak = EconomyManager.getKillstreak(killer);
        Float victimBalance = EconomyManager.getBalance(victim);

        Float takeFromVictim = victimBalance == 0f ? 0f : victimBalance / 100;

        EconomyManager.setBalance(victim, victimBalance - takeFromVictim);

        victim.sendTitle(ChatColor.RED + "You Died", ChatColor.GOLD + killer.getDisplayName() + " stole " + ChatColor.WHITE + "$" + Methods.rStr(takeFromVictim));

        //Killer Rewards
        float reward = 10f + killerKillstreak + EconomyManager.getKillstreak(victim);

        float levelBonus = EconomyManager.getLevel(victim) - EconomyManager.getLevel(killer) < -0.75f ? -0.75f : (EconomyManager.getLevel(victim) - EconomyManager.getLevel(killer)) / 10f;

        float multi = 1 + levelBonus;
        float moneyMulti = 1 + multi;
        float xpMulti = 1 + multi;

        float money = reward * moneyMulti + takeFromVictim, xp = reward * xpMulti;

        killer.sendMessage(Methods.cStr("&6You killed " + victim.getDisplayName() + " &6."));
        killer.sendActionBar(Methods.cStr("&f+$" + Methods.rStr(money) + " &7(" + Methods.rStr(moneyMulti) + "x) &8| &f+" + Methods.rStr(xp) + "xp &7(" + Methods.rStr(xpMulti) + "x) &8| &6Streak&7: &f" + Methods.rStr((float) killerKillstreak)));
        EconomyManager.setBalance(killer, EconomyManager.getBalance(killer) + money);
        EconomyManager.setXp(killer, EconomyManager.getXp(killer) + xp);
        EconomyManager.setKillstreak(killer, killerKillstreak + 1);
        LevelHandler.xp(killer);
    }
}
