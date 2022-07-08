package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.utilities.Methods;
import io.github.thelordman.costrength.utilities.data.PlayerDataManager;
import io.github.thelordman.costrength.utilities.data.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 5)) return true;
        if(sender instanceof Player player) {
            if (Rank.getRank(player.getUniqueId()).permissionLevel > 5) {
                if (!PlayerDataManager.getPlayerData(player.getUniqueId()).inStaffMode()) {
                    VanishCommand.toggleVanish(player);
                    if (VanishCommand.getVanishedPlayers().contains(player)) {
                        player.saveData();
                        player.getInventory().clear();
                        return true;
                    }
                    player.loadData();
                    return true;
                }
                PlayerDataManager.getPlayerData(player.getUniqueId()).setStaffMode(false);
                return true;
            }
        }
        return false;
    }
}
