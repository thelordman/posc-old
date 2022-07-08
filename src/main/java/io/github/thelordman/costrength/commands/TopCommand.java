package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class TopCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player executor)) {
            commandSender.sendMessage(Methods.cStr("&cOnly players may execute this command"));
            return true;
        }
        if (!Methods.checkCommandPermission(executor, 3)) return true;

        Player target;
        if (strings.length == 0) {
            target = executor;
            executor.sendMessage(Methods.cStr("&aYou have been teleported to the top!"));
        } else {
            target = Bukkit.getPlayer(strings[0]);
            if (target == null) {
                executor.sendMessage(Methods.cStr(String.format("&7Player &6%s &7is not online", strings[0])));
                return true;
            }
            executor.sendMessage(Methods.cStr(String.format("&aYou have teleported %s to the top", strings[0])));
            target.sendMessage(Methods.cStr("&aYou have been teleported to the top!"));
        }

        target.teleportAsync(target.getWorld().getHighestBlockAt(target.getLocation()).getLocation().add(0, 1, 0));

        return true;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) return null;

        return Collections.emptyList();
    }
}
