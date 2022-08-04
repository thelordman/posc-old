package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.date.Date;
import io.github.thelordman.costrength.punishments.Punishment;
import io.github.thelordman.costrength.punishments.PunishmentManager;
import io.github.thelordman.costrength.punishments.PunishmentType;
import io.github.thelordman.costrength.utilities.CommandName;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Arrays;

@CommandName("mute")
public class MuteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 2)) return true;
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (PunishmentManager.isMuted(target.getUniqueId())) {
            sender.sendMessage(Methods.cStr("&cTarget is already muted. /unmute to unmute."));
            return true;
        }

        if (Date.SecsFromString(args[1]) == 0) return false;
        int time = (int) (Instant.now().getEpochSecond() + Date.SecsFromString(args[1]));
        String reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

        Bukkit.broadcastMessage(Methods.cStr("&f" + target.getName() + " &6has been muted by &f" + sender.getName() + " &6for &f" +
                reason + " &8| &f" + args[1] + "&6."));
        PunishmentManager.setMuted(target.getUniqueId(), time);
        PunishmentManager.addPunishment(target.getUniqueId(), new Punishment(PunishmentType.MUTE, (int) Instant.now().getEpochSecond(),
                time, reason.isEmpty() ? null : reason, sender instanceof Player ? ((Player) sender).getUniqueId() : null, 1));

        return true;
    }
}