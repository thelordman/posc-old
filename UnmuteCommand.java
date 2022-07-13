package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.utilities.CommandName;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandName("umute")
public class UnmuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (Methods.checkCommandPermission(sender, (byte) 4)) return true;

        if (args.length > 1) return true;

        if (Bukkit.getPlayer(args[0]) == null) return true;

        Player target = Bukkit.getPlayer(args[0]);
        Player player = (Player) sender;

        if (!target.getScoreboardTags().contains("muted")) {
            player.sendMessage(Methods.cStr(target.getDisplayName() + "&6 isn't muted."));
            return true;
        }
        target.removeScoreboardTag("muted");
        target.sendMessage(Methods.cStr("&6You have been unmuted by a staff member"));
        player.sendMessage(Methods.cStr("&6You have unmuted &f" + target.getDisplayName()));
        return true;
    }
}
