package me.lord.posc.commands;

import me.lord.posc.Posc;
import me.lord.posc.data.DataManager;
import me.lord.posc.npc.NPC;
import me.lord.posc.npc.NPCManager;
import me.lord.posc.utilities.Cmd;
import me.lord.posc.utilities.CommandUtil;
import me.lord.posc.utilities.TextUtil;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NPCCommand implements Cmd {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return false;

        switch (args[0]) {
            case "create" -> {
                if (!(sender instanceof Player player)) return false;
                if (args.length == 1) return false;

                String name = args[1];
                Location location = player.getLocation();
                String skin = null;
                if (args.length > 2) skin = args[2];

                NPCManager.createNPC(name, location, skin);
                player.sendMessage(TextUtil.c("&eNPC &6" + name + " &ecreated at your location."));
            }
            case "sel" -> {
                NPC target;

                if (args.length == 1) {
                    if (!(sender instanceof Player player)) return false;

                    NPC targetNPC = NPCManager.getTargetNPC(player);
                    if (targetNPC != null) {
                        target = targetNPC;
                    } else {
                        target = NPCManager.getNPCMap().values().stream()
                                .min(Comparator.comparingDouble(o -> o.getLocation().distanceSquared(player.getLocation())))
                                .orElse(null);
                    }

                    if (target == null) {
                        player.sendMessage(TextUtil.c("&cNo NPC found.\n&cTry /npc sel [id | name]"));
                        break;
                    }
                } else {
                    if (TextUtil.isNumber(args[1])) {
                        int index = Integer.parseInt(args[1]);

                        target = NPCManager.getNPC(index);

                        if (target == null) {
                            target = handleNPCFromName(args, sender);
                            if (target == null) break;
                        }
                    } else {
                        target = handleNPCFromName(args, sender);
                        if (target == null) break;
                    }
                }

                if (sender instanceof Player player) {
                    DataManager.getPlayerData(player).setSelectedNPC(target.getIndex());
                } else {
                    DataManager.getGlobal().setConsoleSelectedNPC(target.getIndex());
                }

                sender.sendMessage(TextUtil.c("&eSelected &6" + target.getNameString() + " &e(ID: &6" + target.getIndex() + "&e)"));
            }
        }

        return true;
    }

    private NPC handleNPCFromName(String[] args, CommandSender sender) {
        String name = args[1].replace("\"", "");

        NPC[] npcs = NPCManager.getNPC(name);
        if (npcs.length == 0) {
            sender.sendMessage(TextUtil.c("&cNo NPC with that name or ID found.\n&cDo /npc list for a list of NPCs."));
            return null;
        }
        if (npcs.length == 1) {
            return npcs[0];
        }
        sender.sendMessage(TextUtil.c("\n&e&lClick to select NPC\n"));
        for (NPC npc : npcs) {
            sender.sendMessage(TextUtil.c("&6" + npc.getIndex() + " &e- &6" + npc.getNameString() + " &e- &6" + TextUtil.sexyLocation(npc.getLocation()))
                    .hoverEvent(HoverEvent.showText(TextUtil.c("&eSelect")))
                    .clickEvent(ClickEvent.runCommand("/npc sel " + npc.getIndex())));
        }
        sender.sendMessage(TextUtil.empty());
        return null;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return switch (args.length) {
            case 1 -> CommandUtil.partialMatches(args[0], List.of("create", "sel", "list", "delete", "skin", "help"));
            case 2 -> switch (args[0]) {
                case "sel", "delete" -> CommandUtil.partialMatches(args[0], NPCManager.getNPCMap().values().stream()
                        .map(NPC::getIndex)
                        .map(String::valueOf)
                        .toList());
                default -> Collections.emptyList();
            };
            default -> Collections.emptyList();
        };
    }

    @Override
    public String name() {
        return "npc";
    }

    @Override
    public String permission() {
        return "posc.command.npc";
    }
}
