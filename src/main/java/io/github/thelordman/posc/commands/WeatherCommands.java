package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandName("sun")
public class WeatherCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 5)) return true;
        World world = Bukkit.getWorld("world");
        Player player = (Player) sender;
        switch(command.getName()) {
            case "sun":
                world.setStorm(false);
                player.sendMessage(Methods.cStr("&6The weather was set to &fsunny"));
                break;
            case "rain":
                world.setStorm(true);
                player.sendMessage(Methods.cStr("&6The weather was set to &frain"));
                break;
            case "thunder":
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