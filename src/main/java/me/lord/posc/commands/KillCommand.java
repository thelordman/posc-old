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

public class KillCommand implements Cmd {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0 && label.equals("suicide") && (!args[0].equals(sender.getName()) || !(sender instanceof Player))) return false;
        if (!(sender instanceof Player) && args.length == 0) return CommandUtil.error(sender, CommandUtil.Error.PLAYER_ONLY);
        Player target = args.length == 0 ? (Player) sender : Bukkit.getPlayer(args[0]);
        if (target == null) return CommandUtil.error(sender, CommandUtil.Error.PLAYER_NOT_ONLINE);

        if (target != sender) sender.sendMessage(TextUtil.c("&6" + target.getName() + " &ehas been killed."));
        target.sendMessage(TextUtil.c("&eYou were killed by " + (target != sender ? "&6" + sender.getName() + "&e." : "yourself.")));
        target.setHealth(0d);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equals("suicide")) return Collections.emptyList();
        return args.length == 1 ? null : Collections.emptyList();
    }

    @Override
    public String getName() {
        return "kill";
    }
}
