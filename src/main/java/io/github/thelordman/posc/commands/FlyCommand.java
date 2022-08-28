package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.utilities.CommandName;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandName("Fly")
public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("posc.fly")) {
                player.setFlying(!player.isFlying());
                player.sendMessage("&6Flight has been set to " + player.isFlying() ? "&cFalse" : "&atrue");
            } else {
                player.sendMessage("&cYou Can't use this command");
                return false;
            }

        } else {
            sender.sendMessage("Console can't fly dingus!");
            return false;
        }

        return true;
    }

}
