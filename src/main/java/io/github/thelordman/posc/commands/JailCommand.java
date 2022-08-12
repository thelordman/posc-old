package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.punishments.Punishment;
import io.github.thelordman.posc.punishments.PunishmentManager;
import io.github.thelordman.posc.punishments.PunishmentType;
import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import io.github.thelordman.posc.utilities.Rank;
import io.github.thelordman.posc.utilities.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

@CommandName("jail")
public class JailCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Rank.hasPermission(sender, (byte) 2)) return true;
        Player player = (Player) sender;

        if (args.length < 1) {
            DataManager.setJailLocation(player.getLocation());
            player.sendMessage(Methods.cStr("&6Jail was set to &f" + Methods.locToString(DataManager.getJailLocation(), false)));
            return true;
        }

        return jail(player, Bukkit.getPlayer(args[0]));
    }

    private boolean isJailed(OfflinePlayer target) {
        if (target.hasPlayedBefore()) {
            return target.getPlayer().getScoreboardTags().contains("isJailed");
        }
        return false;
    }

    private boolean jail(Player player, Player target) {
        if (target == null) {
            return false;
        }

        if (isJailed(target)) {
            target.removeScoreboardTag("isJailed");
            Methods.teleportPlayerToSpawn(target, PlayerTeleportEvent.TeleportCause.COMMAND);
            player.sendMessage(Methods.cStr("&6You unjailed the player &f" + target.getDisplayName() + "&6."));
            target.sendMessage(Methods.cStr("&6You have been unjailed by &f" + player.getDisplayName() + "&6."));
        } else {
            target.addScoreboardTag("isJailed");
            target.teleportAsync(DataManager.getJailLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
            player.sendMessage(Methods.cStr("&6You jailed the player &f" + target.getDisplayName() + "&6. &8| &f" + Methods.locToString(DataManager.getJailLocation(), false)));
            target.sendMessage(Methods.cStr("&6You have been jailed by &f" + player.getDisplayName() + "&6. &8| &f" + Methods.locToString(DataManager.getJailLocation(), false)));
            PunishmentManager.addPunishment(target.getUniqueId(), new Punishment(PunishmentType.JAIL, null, null, player.getUniqueId()));
        }

        return true;
    }
}