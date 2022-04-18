package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WeatherCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        World world = Bukkit.getWorld("world");
        Player player = (Player) sender;
        switch(command.getName()) {
            case "sun", "clean", "clouds":
                world.setStorm(false);
                player.sendMessage(Methods.cStr("&6The weather was set to &fsunny"));
                break;
            case "rain", "snow", "downfall":
                world.setStorm(true);
                player.sendMessage(Methods.cStr("&6The weather was set to &frain"));
                break;
            case "thunder", "storm":
                world.setStorm(true);
                world.setThundering(true);
                player.sendMessage(Methods.cStr("&6The weather was set to &fthunder"));
                break;
            default:
                return false;

        }
        return true;
    }
}