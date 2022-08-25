package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.punishments.PunishmentManager;
import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import io.github.thelordman.posc.utilities.Rank;
import io.github.thelordman.posc.utilities.data.DataManager;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandName("unipban")
public class UnIpBanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!Rank.hasPermission(sender, (byte) 5)) return true;
        if (args.length == 0) return false;

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!sender.getName().equals("My_Lord") && Rank.getRank(target.getUniqueId()).permissionLevel >= Rank.getRank(((Player) sender).getUniqueId()).permissionLevel) {
            sender.sendMessage(Methods.cStr("&cYou cannot unban that IP."));
            return true;
        }
        if (DataManager.getAddress(target) == null) {
            sender.sendMessage(Methods.cStr("&cTarget hasn't logged on the server yet, which means it's impossible to know their IP address."));
            return true;
        }
        if (!Bukkit.getBanList(BanList.Type.IP).isBanned(DataManager.getAddress(target))) {
            sender.sendMessage(Methods.cStr("&cTarget's IP isn't banned. /ipban to ban."));
            return true;
        }

        String msg = Methods.cStr("&f" + target.getName() + "'s IP &6has been unbanned by &f" + sender.getName());
        if (args[args.length - 1].equals("-s")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (Rank.getRank(player.getUniqueId()).permissionLevel > 0) player.sendMessage(msg + Methods.cStr(" &7[&6Silent&7]"));
            }
        } else Bukkit.broadcastMessage(msg);

        if (target.isOnline()) target.getPlayer().sendMessage(Methods.cStr("\n&cYour IP has been unbanned by &f" + sender.getName() + "&c.\n"));
        Bukkit.getBanList(BanList.Type.IP).pardon(DataManager.getAddress(target));

        return true;
    }
}