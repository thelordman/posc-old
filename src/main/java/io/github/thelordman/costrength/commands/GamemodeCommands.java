package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender && args.length == 0) return false;

        GameMode mode;
        switch (cmd.getName()) {
            case "gmc":
                mode = GameMode.CREATIVE;
                break;
            case "gms":
                mode = GameMode.SURVIVAL;
                break;
            case "gma":
                mode = GameMode.ADVENTURE;
                break;
            case "gmsp":
                mode = GameMode.SPECTATOR;
                break;
            default:
                return false;
        }

        return updateMode(Methods.determineTarget(sender, args.length > 0 ? args[0] : sender.getName()), sender, mode);
    }

    private boolean updateMode(Player target, CommandSender sender, GameMode mode) {
        if (target == null) {
            return false;
        }

        target.setGameMode(mode);
        target.sendMessage(Methods.cStr("&6Your gamemode was changed to &f" + Methods.rfStr(mode.name()) + "&6."));

        if (target != sender) {
            sender.sendMessage(Methods.cStr("&f" + target.getDisplayName() + "'s &6gamemode was changed to &f" + Methods.rfStr(mode.name()) + "&6."));
        }

        return true;
    }
}
