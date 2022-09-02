package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.utilities.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlySpeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Rank.hasPermission(sender, 5)) return true;
        if (args.length == 0) return false;

        Player player = (Player) sender;
        float speed;
        try {
            speed = Float.parseFloat(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage("&cPlease pick a number from -10 to 10");
            return false;
        }
        if (speed > 10 || speed < -10) {
            sender.sendMessage("&cPlease pick a number from -10 to 10");
            return false;
        }

        player.setFlySpeed(speed / 10);
        player.sendMessage("&6Set speed to &f " + speed + "&6.");
        return true;
    }
}
