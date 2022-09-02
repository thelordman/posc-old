package io.github.thelordman.posc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlySpeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("posc.flyspeed")) {
                int speed;
                try {
                    speed = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    player.sendMessage("&cPlease pick a number 1-10");
                    return false;
                }
                if (speed > 10 || speed < 1) {
                    player.sendMessage("&cPlease pick a number 1-10");
                    return false;
                }

                player.setFlySpeed((float) speed / 10);
                player.sendMessage("&6Set speed to &f " + speed);

            }

        }

        return true;
    }

}
