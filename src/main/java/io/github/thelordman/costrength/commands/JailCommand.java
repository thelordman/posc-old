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
        Player player = (Player) sender;

        if (args.length > 1) {
            jailLocation = player.getLocation();
            player.sendMessage(Methods.cStr("&6Jail was set to your location."));
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
        if (isJailed(target)) {
            target.removeScoreboardTag("isJailed");
            Methods.teleportPlayerToSpawn(target, PlayerTeleportEvent.TeleportCause.COMMAND);
        } else {
            target.addScoreboardTag("isJailed");
            target.teleportAsync(jailLocation, PlayerTeleportEvent.TeleportCause.COMMAND);
            player.sendMessage("You jailed the player "+target.getName());
            target.sendMessage("You have been jailed");
        }
        return true;
    }
}