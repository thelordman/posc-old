package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.punishments.PunishmentManager;
import io.github.thelordman.costrength.utilities.CommandName;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@CommandName("unmute")
public class UnmuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 2)) return true;
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!PunishmentManager.isMuted(target.getUniqueId())) {
            sender.sendMessage(Methods.cStr("&cTarget isn't muted. /mute to mute."));
            return true;
        }

        Bukkit.broadcastMessage(Methods.cStr("&f" + target.getName() + " &6has been unmuted by &f" + sender.getName()));
        PunishmentManager.setMuted(target.getUniqueId(), 0L);

        return true;
    }
}