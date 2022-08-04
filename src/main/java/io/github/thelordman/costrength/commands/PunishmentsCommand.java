package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.date.Date;
import io.github.thelordman.costrength.date.DateType;
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

import java.util.ArrayList;

@CommandName("punishments")
public class PunishmentsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length > 0 && !Methods.checkCommandPermission(sender, (byte) 1)) return true;
        Player player = (Player) sender;
        OfflinePlayer target = args.length == 0 ? player : Bukkit.getOfflinePlayer(args[0]);

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
            String expirationDate = punishment.getExpiration() == -1 ? "&cNever" : Date.dateTimeFormat(punishment.getExpiration(), DateType.FULL);
            String punisher = punishment.getPunisher() == null ? "Console" : Bukkit.getOfflinePlayer(punishment.getPunisher()).getName();
            builder.append("  &6ID&7: &f").append(punishment.getID()).append("\n")
                    .append("   &6Given&7: &f").append(Date.dateTimeFormat(punishment.getCreated(), DateType.FULL)).append("\n")
                    .append("   &6Expiration&7: &f").append(expirationDate).append("\n")
                    .append("   &6Reason&7: &f").append(punishment.getReason()).append("\n")
                    .append("   &6Punisher&7: &f").append(punisher).append("\n");
        }
        return builder.toString();
    }
}