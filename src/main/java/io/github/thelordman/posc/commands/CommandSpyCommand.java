package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.utilities.CommandName;
import io.github.thelordman.posc.utilities.Methods;
import io.github.thelordman.posc.utilities.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandName("commandspy")
public class CommandSpyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Rank.hasPermission(sender, (byte) 6)) return true;
        if (sender instanceof Player player && args.length == 0) {
            if (player.getScoreboardTags().contains("commandSpyToggled")) {
                player.removeScoreboardTag("commandSpyToggled");
                player.sendMessage(Methods.cStr("&6Command Spy disabled."));
            } else {
                player.addScoreboardTag("commandSpyToggled");
                player.sendMessage(Methods.cStr("&6Command Spy enabled."));
            }
            return true;
        }
        return false;
    }
}
