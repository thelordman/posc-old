package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.mining.MineHandler;
import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import io.github.thelordman.posc.utilities.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandName("mine")
public class MineCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Rank.hasDonorPermission(sender, (byte) 3)) return true;
        MineHandler.resetMine((byte) 0, (Player) sender);
        return true;
    }
}