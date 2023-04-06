package me.lord.posc.commands;

import me.lord.posc.utilities.Cmd;
import me.lord.posc.utilities.CommandUtil;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public final class GamemodeCommands implements Cmd {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player) && args.length == 0) return CommandUtil.error(sender, CommandUtil.Error.PLAYER_ONLY);
        Player target = args.length == 0 ? (Player) sender : Bukkit.getPlayer(args[0]);
        if (target == null) return CommandUtil.error(sender, CommandUtil.Error.TARGET_NOT_ONLINE);

        GameMode gameMode = switch (command.getName()) {
            case "gmc" -> GameMode.CREATIVE;
            case "gms" -> GameMode.SURVIVAL;
            case "gma" -> GameMode.ADVENTURE;
            case "gmsp" -> GameMode.SPECTATOR;
            default -> null;
        };

        if (target != sender) sender.sendMessage(TextUtil.c("&6" + target.getName() + "'s &egamemode was changed to &6" + gameMode.name().toLowerCase() + "&e."));
        target.sendMessage(TextUtil.c("&eYour gamemode was changed to &6" + gameMode.name().toLowerCase() + (target != sender ? " &eby &6" + sender.getName() + "&e." : "&e.")));
        target.setGameMode(gameMode);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return args.length == 1 ? null : Collections.emptyList();
    }

    @Override
    public String[] names() {
        return new String[]{"gmc", "gms", "gma", "gmsp"};
    }

    @Override
    public String permissions(@NotNull String command) {
        return switch (command) {
            case "gmc" -> "posc.command.gamemode.creative";
            case "gms" -> "posc.command.gamemode.survival";
            case "gma" -> "posc.command.gamemode.adventure";
            case "gmsp" -> "posc.command.gamemode.spectator";
            default -> null;
        };
    }
}
