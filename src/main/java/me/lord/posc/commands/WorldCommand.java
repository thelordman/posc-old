package me.lord.posc.commands;

import me.lord.posc.Posc;
import me.lord.posc.utilities.Cmd;
import me.lord.posc.utilities.CommandUtil;
import me.lord.posc.utilities.TextUtil;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class WorldCommand implements Cmd {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return false;

        switch (args[0]) {
            case "create" -> {
                if (args.length == 1) return false;

                String name = TextUtil.joinArray(args, 1).replace(" -f", "");

                Bukkit.createWorld(WorldCreator.name(name).type(CommandUtil.hasFlag(args, "f") ? WorldType.FLAT : WorldType.NORMAL));
                sender.sendMessage(TextUtil.c("&eWorld &6" + name + " &ecreated &7(" + (CommandUtil.hasFlag(args, "f") ? "flat" : "normal") + ")"));
            }
            case "list" -> {
                sender.sendMessage(TextUtil.c("\n&e&lWorlds\n"));
                for (World world : Bukkit.getWorlds()) {
                    sender.sendMessage(TextUtil.c("&6" + world.getName() +
                                    (sender instanceof Player player ? (player.getWorld() == world ? " &e&lCurrent" : "") : ""))
                            .hoverEvent(HoverEvent.showText(TextUtil.c("&eJoin")))
                            .clickEvent(ClickEvent.runCommand("/world join " + world.getName())));
                }
                sender.sendMessage(TextUtil.empty());
            }
            case "delete" -> {
                if (args.length == 1) return false;
                World target = Bukkit.getWorld(TextUtil.joinArray(args, 1));

                if (target == null) {
                    sender.sendMessage(TextUtil.c("&cNo world with that name found."));
                    break;
                }

                for (Player player : target.getPlayers()) {
                    player.teleport(Posc.MAIN_WORLD.getSpawnLocation());
                }
                Bukkit.unloadWorld(target, false);
                try {
                    FileUtils.deleteDirectory(target.getWorldFolder());
                } catch (IOException e) {
                    sender.sendMessage(TextUtil.c("&cSomething went wrong with deleting the world files. Check console for more information."));
                    e.printStackTrace();
                    break;
                }
                sender.sendMessage(TextUtil.c("&eWorld deleted successfully."));
            }
            case "join" -> {
                if (args.length == 1 || !(sender instanceof Player player)) return false;

                World target = Bukkit.getWorld(TextUtil.joinArray(args, 1));

                if (target == null) {
                    player.sendMessage(TextUtil.c("&cNo world with that name found."));
                    break;
                }

                player.teleport(target.getSpawnLocation());
                player.sendMessage(TextUtil.c("&eSent you to &6" + target.getName() + "&e."));
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return switch (args.length) {
            case 1 -> CommandUtil.partialMatches(args[0], List.of("create", "list", "delete", "join"));
            case 2 -> switch (args[0]) {
                case "delete", "join" -> CommandUtil.partialMatches(args[1], Bukkit.getWorlds().stream()
                        .map(World::getName)
                        .toList());
                default -> Collections.emptyList();
            };
            default -> Collections.emptyList();
        };
    }

    @Override
    public String name() {
        return "world";
    }

    @Override
    public String permission() {
        return "posc.command.world";
    }
}
