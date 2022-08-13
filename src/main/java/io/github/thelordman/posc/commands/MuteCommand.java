package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.date.Date;
import io.github.thelordman.posc.punishments.Punishment;
import io.github.thelordman.posc.punishments.PunishmentManager;
import io.github.thelordman.posc.punishments.PunishmentType;
import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import io.github.thelordman.posc.utilities.Rank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@CommandName("mute")
public class MuteCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!Rank.hasPermission(sender, (byte) 2)) return true;
        if (args.length < 2) return false;

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!sender.getName().equals("My_Lord") && Rank.getRank(target.getUniqueId()).permissionLevel >= Rank.getRank(((Player) sender).getUniqueId()).permissionLevel) {
            sender.sendMessage(Methods.cStr("&cYou cannot mute that player."));
            return true;
        }
        if (PunishmentManager.isMuted(target.getUniqueId())) {
            sender.sendMessage(Methods.cStr("&cTarget is already muted. /unmute to unmute."));
            return true;
        }

        if (Date.secsFromString(args[1]) == 0 && !args[1].toLowerCase().startsWith("perm")) return false;
        Integer time = Date.nowPlus(Date.secsFromString(args[1]));
        String reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length)).replace("-s", "").isEmpty() ? "No reason"
                : String.join(" ", Arrays.copyOfRange(args, 2, args.length)).replace("-s", "");

        String msg = Methods.cStr("&f" + target.getName() + " &6has been muted by &f" + sender.getName() +
                " &6for &f" + reason + " &8| &f" + args[1] + "&6.");
        if (args[args.length - 1].equals("-s")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Rank.getRank(player.getUniqueId()).permissionLevel > 0) player.sendMessage(msg + Methods.cStr(" &7[&6Silent&7]"));
            }
        } else Bukkit.broadcastMessage(msg);

        if (target.isOnline()) target.getPlayer().sendMessage(Methods.cStr("\n&cYou've been muted by &f" + sender.getName() +
                " &cfor &f" + reason + " &8| &f" + args[1] + "&c.\n"));
        PunishmentManager.setMuted(target.getUniqueId(), time);

        PunishmentManager.addPunishment(target.getUniqueId(), new Punishment(PunishmentType.MUTE,
                args[1].toLowerCase().startsWith("perm") ? null : time, reason,
                sender instanceof Player ? ((Player) sender).getUniqueId() : null));

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return switch (args.length) {
            case 1 -> null;
            case 2 -> StringUtil.copyPartialMatches(args[1], List.of("perm"), new ArrayList<>());
            case 4 -> StringUtil.copyPartialMatches(args[1], List.of("-s"), new ArrayList<>());
            default -> Collections.emptyList();
        };
    }
}