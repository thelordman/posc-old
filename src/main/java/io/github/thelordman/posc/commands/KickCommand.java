package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.punishments.Punishment;
import io.github.thelordman.posc.punishments.PunishmentManager;
import io.github.thelordman.posc.punishments.PunishmentType;
import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import io.github.thelordman.posc.utilities.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;

@CommandName("kick")
public class KickCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Rank.hasPermission(sender, (byte) 2)) return true;
        if (args.length == 0) return false;

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) return false;

        if (!sender.getName().equals("My_Lord") && Rank.getRank(target.getUniqueId()).permissionLevel >= Rank.getRank(((Player) sender).getUniqueId()).permissionLevel) {
            sender.sendMessage(Methods.cStr("&cYou cannot kick that player."));
            return true;
        }

        String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length)).replace("-s", "").isEmpty() ? "No reason"
                : String.join(" ", Arrays.copyOfRange(args, 1, args.length)).replace("-s", "");

        String msg = Methods.cStr("&f" + target.getName() + " &6has been kicked by &f" + sender.getName() + " &6for &f" + reason + "&6.");
        if (args[args.length - 1].equals("-s")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Rank.getRank(player.getUniqueId()).permissionLevel > 0) player.sendMessage(msg + Methods.cStr(" &7[&6Silent&7]"));
            }
        } else Bukkit.broadcastMessage(msg);

        target.kickPlayer(reason);

        PunishmentManager.addPunishment(target.getUniqueId(), new Punishment(PunishmentType.KICK,
                null, reason, sender instanceof Player ? ((Player) sender).getUniqueId() : null));

        return true;
    }
}