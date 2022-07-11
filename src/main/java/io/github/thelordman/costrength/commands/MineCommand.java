package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.mining.MineHandler;
import io.github.thelordman.costrength.utilities.CommandName;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandName("mine")
public class MineCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (Methods.checkDonatorCommandPermission(sender, (byte) 3)) return true;
        MineHandler.resetMine((byte) 0, (Player) sender);
        return true;
    }
}