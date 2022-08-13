package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import io.github.thelordman.posc.utilities.data.DataManager;
import io.github.thelordman.posc.utilities.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandName("staffmode")
public class StaffModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Rank.hasPermission(sender, (byte) 5)) return true;
        if(sender instanceof Player player) {
            if (Rank.getRank(player.getUniqueId()).permissionLevel > 5) {
                if (!DataManager.getPlayerData(player.getUniqueId()).inStaffMode()) {
                    VanishCommand.toggleVanish(player);
                    if (VanishCommand.getVanishedPlayers().contains(player)) {
                        player.saveData();
                        player.getInventory().clear();
                        return true;
                    }
                    player.loadData();
                    return true;
                }
                DataManager.getPlayerData(player.getUniqueId()).setStaffMode(false);
                return true;
            }
        }
        return false;
    }
}
