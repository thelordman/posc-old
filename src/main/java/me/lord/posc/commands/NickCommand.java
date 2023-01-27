package me.lord.posc.commands;

import me.lord.posc.utilities.Cmd;
import me.lord.posc.utilities.CommandUtil;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class NickCommand implements Cmd {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            if (sender instanceof Player player)
                player.setDisplayName(args[0]);
        }
        return true;
    }

    @Override
    public String getName() {
        return "nick";
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
    }
}
