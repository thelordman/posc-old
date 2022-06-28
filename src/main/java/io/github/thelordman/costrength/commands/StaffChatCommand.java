package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.ranks.RankManager;
import io.github.thelordman.costrength.utilities.Methods;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand implements CommandExecutor {
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length > 0) {
            if(sender instanceof Player) {
                if(RankManager.hasPermission((Player) sender, (byte) 1)) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (RankManager.hasPermission(player, (byte) 1)) {
                            player.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', "&e[STAFFCHAT] &6" + sender.getName() + " &7› &f" + args)));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
