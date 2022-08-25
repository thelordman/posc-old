package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.date.Date;
import io.github.thelordman.posc.punishments.Punishment;
import io.github.thelordman.posc.punishments.PunishmentManager;
import io.github.thelordman.posc.punishments.PunishmentType;
import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import io.github.thelordman.posc.utilities.Rank;
import io.github.thelordman.posc.utilities.data.DataManager;
import org.bukkit.BanList;
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

@CommandName("ipban")
public class IpBanCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!Rank.hasPermission(sender, (byte) 5)) return true;
        if (args.length == 0) return false;

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!sender.getName().equals("My_Lord") && Rank.getRank(target.getUniqueId()).permissionLevel >= Rank.getRank(((Player) sender).getUniqueId()).permissionLevel) {
            sender.sendMessage(Methods.cStr("&cYou cannot IP-ban that player."));
            return true;
        }
        if (DataManager.getAddress(target) == null) {
            sender.sendMessage(Methods.cStr("&cTarget hasn't logged on the server yet, which means it's impossible to know their IP address."));
            return true;
        }
        if (Bukkit.getBanList(BanList.Type.IP).isBanned(DataManager.getAddress(target))) {
            sender.sendMessage(Methods.cStr("&cTarget's IP is already banned. /unipban to unban the ip."));
            return true;
        }

        Integer time = Date.punishmentLength(args);
        if (time != null) if (time == -1) return false;

        String reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length)).replace("-s", "").isEmpty() ? "No reason"
                : String.join(" ", Arrays.copyOfRange(args, 2, args.length)).replace("-s", "");

        String msg = Methods.cStr("&f" + target.getName() + " &6has been IP-banned by &f" + sender.getName() +
                " &6for &f" + reason + " &8| &f" + args[1] + "&6.");
        if (args[args.length - 1].equals("-s")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Rank.getRank(player.getUniqueId()).permissionLevel > 0) player.sendMessage(msg + Methods.cStr(" &7[&6Silent&7]"));
            }
        } else Bukkit.broadcastMessage(msg);
        if (target.isOnline()) target.getPlayer().sendMessage(Methods.cStr("\n&cYou've been IP-banned by &f" + sender.getName() +
                " &cfor &f" + reason + " &8| &f" + args[1] + "&c.\n"));

        Bukkit.banIP(DataManager.getAddress(target));
        if (target.isOnline()) target.getPlayer().kickPlayer(reason);
        PunishmentManager.addPunishment(target.getUniqueId(), new Punishment(PunishmentType.IP_BAN,
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