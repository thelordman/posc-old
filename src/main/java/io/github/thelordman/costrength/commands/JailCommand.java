package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;


public class JailCommand implements CommandExecutor {
    public Location jailLocation;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 2)) return true;
        Player player = (Player) sender;

        if (args.length < 1) {
            jailLocation = player.getLocation();
            player.sendMessage(Methods.cStr("&6Jail was set to &f" + Methods.locToString(jailLocation, false)));
            return true;
        }

        return jail(player, Methods.determineTarget(sender, args[0]));
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
            target.teleportAsync(jailLocation, PlayerTeleportEvent.TeleportCause.COMMAND);
            player.sendMessage(Methods.cStr("&6You jailed the player &f" + target.getDisplayName() + "&6. &8| &f" + Methods.locToString(jailLocation, false)));
            target.sendMessage(Methods.cStr("&6You have been jailed by &f" + player.getDisplayName() + "&6. &8| &f" + Methods.locToString(jailLocation, false)));
        }

        return true;
    }
}