package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.utilities.CommandName;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandName("tempmute")
public class TempmuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (Methods.checkCommandPermission(sender, (byte) 4)) return true;

//        /tempmute <player> <duration|permanent> <reason>

        if (args.length < 2) return true;

        Player target = Bukkit.getPlayer(args[0]);
        Player player = (Player) sender;
        String time = args[1];
        String reason = args[2];

        if (target != null && time != null && reason != null) {

            if (target.getScoreboardTags().contains("muted")) {
                player.sendMessage(Methods.cStr(target.getDisplayName() + "&6 is already muted."));
                return true;
            }

            target.addScoreboardTag("muted");
            target.sendMessage(Methods.cStr("&6You have been muted by a staff member for: &f" + reason + "&6during:&f" + time));
            return true;
        }
        return false;
    }
}
