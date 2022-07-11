package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.utilities.CommandName;
import io.github.thelordman.costrength.utilities.Methods;
import io.github.thelordman.costrength.utilities.data.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandName("reload-config")
public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 5)) return true;
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(Rank.getRank(player.getUniqueId()).permissionLevel >= 2) {
                player.sendMessage(Methods.cStr("&6[StaffChat] &e" + sender.getName() + "&7: &f" + String.join(" ", args)));
            }
        }
        return true;
    }
}
