package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.date.Date;
import io.github.thelordman.posc.date.DateType;
import io.github.thelordman.posc.punishments.Punishment;
import io.github.thelordman.posc.punishments.PunishmentManager;
import io.github.thelordman.posc.punishments.PunishmentType;
import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import io.github.thelordman.posc.utilities.Rank;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CommandName("punishments")
public class PunishmentsCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length > 0 && !Rank.hasPermission(sender, (byte) 1)) return true;
        if (args.length > 1 && !Rank.hasPermission(sender, (byte) 6)) return true;
        Player player = (Player) sender;
        OfflinePlayer target = args.length == 0 ? player : Bukkit.getOfflinePlayer(args[0]);
        if (args.length > 1 && !sender.getName().equals("My_Lord") && Rank.getRank(target.getUniqueId()).permissionLevel >= Rank.getRank(((Player) sender).getUniqueId()).permissionLevel) {
            sender.sendMessage(Methods.cStr("&cYou cannot modify the punishments of that player."));
            return true;
        }

        if (args.length > 1) {
            if (args.length < 3 | !args[1].equals("remove")) return false;

            try {
                return PunishmentManager.removePunishment(target.getUniqueId(), Integer.parseInt(args[2]));
            } catch (NumberFormatException e) {
                return false;
            }
        }

        sender.sendMessage(Methods.cStr("\n&r    &6&l" + target.getName() + "'s Punishments\n&r\n" +
                " &6Warnings &7(&f" + PunishmentManager.getPunishmentAmount(target.getUniqueId(), PunishmentType.WARNING) + "&7):\n" +
                punishmentEntries(PunishmentManager.getPunishments(target.getUniqueId(), PunishmentType.WARNING)) +
                " &6Jails &7(&f" + PunishmentManager.getPunishmentAmount(target.getUniqueId(), PunishmentType.JAIL) + "&7):\n" +
                punishmentEntries(PunishmentManager.getPunishments(target.getUniqueId(), PunishmentType.JAIL)) +
                " &6Kicks &7(&f" + PunishmentManager.getPunishmentAmount(target.getUniqueId(), PunishmentType.KICK) + "&7):\n" +
                punishmentEntries(PunishmentManager.getPunishments(target.getUniqueId(), PunishmentType.KICK)) +
                " &6Mutes &7(&f" + PunishmentManager.getPunishmentAmount(target.getUniqueId(), PunishmentType.MUTE) + "&7):\n" +
                punishmentEntries(PunishmentManager.getPunishments(target.getUniqueId(), PunishmentType.MUTE)) +
                " &6Bans &7(&f" + PunishmentManager.getPunishmentAmount(target.getUniqueId(), PunishmentType.BAN) + "&7):\n" +
                punishmentEntries(PunishmentManager.getPunishments(target.getUniqueId(), PunishmentType.BAN)) +
                " &6IP-Bans &7(&f" + PunishmentManager.getPunishmentAmount(target.getUniqueId(), PunishmentType.IP_BAN) + "&7):\n" +
                punishmentEntries(PunishmentManager.getPunishments(target.getUniqueId(), PunishmentType.IP_BAN)) + "&r"));

        return true;
    }

    private String punishmentEntries(ArrayList<Punishment> punishments) {
        if (punishments == null) return "  &6No punishments found.\n";
        StringBuilder builder = new StringBuilder();
        for (Punishment punishment : punishments) {
            String expirationDate = punishment.getExpiration() == null ? "&cNever" : Date.dateTimeFormat(punishment.getExpiration(), DateType.FULL);
            String punisher = punishment.getPunisher() == null ? "Console" : Bukkit.getOfflinePlayer(punishment.getPunisher()).getName();
            builder.append("  &6ID&7: &f").append(punishment.getID()).append("\n")
                    .append("   &6Given&7: &f").append(Date.dateTimeFormat(punishment.getCreated(), DateType.FULL)).append("\n");
                    if (punishment.getPunismentType() != PunishmentType.JAIL) builder.append("   &6Expiration&7: &f").append(expirationDate).append("\n");
                    if (punishment.getReason() != null) builder.append("   &6Reason&7: &f").append(punishment.getReason()).append("\n");
                    builder.append("   &6Punisher&7: &f").append(punisher).append("\n");
        }
        return builder.toString();
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return switch (args.length) {
            case 1 -> null;
            case 2 -> StringUtil.copyPartialMatches(args[1], List.of("remove"), new ArrayList<>());
            default -> Collections.emptyList();
        };
    }
}