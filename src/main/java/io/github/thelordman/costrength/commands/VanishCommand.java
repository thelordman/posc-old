package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.utilities.CommandName;
import io.github.thelordman.costrength.utilities.Methods;
import io.github.thelordman.costrength.utilities.data.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@CommandName("vanish")
public class VanishCommand implements CommandExecutor {

    private static ArrayList<Player> vanishedPlayers = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 5)) return true;
        if (!(sender instanceof Player) && args.length == 0) return false;

        return toggleVanish(args.length > 0 ? Bukkit.getPlayer(args[0]) : (Player) sender);
    }

    public static boolean toggleVanish(Player player) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (getVanishedPlayers().contains(player)) {
                onlinePlayer.showPlayer(CoStrength.get(), player);
                if (Rank.getRank(onlinePlayer.getUniqueId()).permissionLevel > 5) {
                    onlinePlayer.sendMessage(Methods.cStr("&f" + player.getDisplayName() + " &6is no longer vanished."));
                }
                getVanishedPlayers().remove(player);
                return true;
            }
            if (Rank.getRank(onlinePlayer.getUniqueId()).permissionLevel < Rank.getRank(player.getUniqueId()).permissionLevel) {
                onlinePlayer.hidePlayer(CoStrength.get(), player);
            } else {
                onlinePlayer.sendMessage(Methods.cStr("&f" + player.getDisplayName() + " &6is now vanished."));
            }
            getVanishedPlayers().add(player);
            return true;
        }
        return false;
    }

    public static ArrayList<Player> getVanishedPlayers() {
        return vanishedPlayers;
    }
}