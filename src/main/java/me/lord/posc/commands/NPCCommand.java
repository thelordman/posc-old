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

                String name = TextUtil.joinArray(args, 1);
                Location location = player.getLocation();

                DataManager.getPlayerData(player).setSelectedNPC(NPCManager.createNPC(name, location, name));
                player.sendMessage(TextUtil.c("&eNPC &6" + name + " &ecreated at your location."));
            }
            case "sel" -> {
                NPC target;

                if (args.length == 1) {
                    if (!(sender instanceof Player player)) return CommandUtil.error(sender, CommandUtil.Error.PLAYER_ONLY);

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
                    String targetName = TextUtil.joinArray(args, 1);

                    if (TextUtil.isNumber(targetName)) {
                        int index = Integer.parseInt(targetName);

                        target = NPCManager.getNPC(index);

                        if (target == null) {
                            target = handleNPCFromName(targetName, sender, false);
                            if (target == null) break;
                        }
                    } else {
                        target = handleNPCFromName(targetName, sender, false);
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
            case "list" -> {
                sender.sendMessage(TextUtil.c("\n&e&lNPCs\n"));
                for (NPC npc : NPCManager.getNPCMap().values()) {
                    sender.sendMessage(TextUtil.c("&6" + npc.getIndex() + " &e- &6" + npc.getNameString() + " &e| &6" + TextUtil.sexyLocation(npc.getLocation()))
                            .hoverEvent(HoverEvent.showText(TextUtil.c("&eSelect")))
                            .clickEvent(ClickEvent.runCommand("/npc sel " + npc.getIndex())));
                }
                sender.sendMessage(TextUtil.empty());
            }
            case "delete" -> {
                NPC target;

                if (args.length == 1) {
                    Integer selectedIndex = sender instanceof Player player ? DataManager.getPlayerData(player).getSelectedNPC() : DataManager.getGlobal().getConsoleSelectedNPC();
                    NPC selectedNPC = null;
                    if (selectedIndex != null) {
                        selectedNPC = NPCManager.getNPC(selectedIndex);
                    }
                    if (selectedIndex == null || selectedNPC == null) {
                        sender.sendMessage(TextUtil.c("&cYou must select an NPC first, or do /npc delete [id | name]"));
                        break;
                    }
                    target = selectedNPC;
                } else {
                    String targetName = TextUtil.joinArray(args, 1);

                    if (TextUtil.isNumber(targetName)) {
                        int index = Integer.parseInt(targetName);

                        target = NPCManager.getNPC(index);

                        if (target == null) {
                            target = handleNPCFromName(targetName, sender, true);
                            if (target == null) break;
                        }
                    } else {
                        target = handleNPCFromName(targetName, sender, true);
                        if (target == null) break;
                    }
                }

                NPCManager.removeNPC(target.getIndex());
                sender.sendMessage(TextUtil.c("&eDeleted &6" + target.getNameString() + " &e(ID: &6" + target.getIndex() + "&e)"));
            }
            case "rename" -> {
                if (args.length == 1) return false;
                NPC target = null;
                Integer selectedIndex = sender instanceof Player player ? DataManager.getPlayerData(player).getSelectedNPC() : DataManager.getGlobal().getConsoleSelectedNPC();
                String name = TextUtil.joinArray(args, 1);
                if (name.length() > 16) {
                    sender.sendMessage(TextUtil.c("&cName is too long (limit is 16 characters)"));
                    break;
                }

                if (selectedIndex != null) {
                    target = NPCManager.getNPC(selectedIndex);
                }
                if (selectedIndex == null || target == null) {
                    sender.sendMessage(TextUtil.c("&cYou must select an NPC first"));
                    break;
                }

                sender.sendMessage(TextUtil.c("&6" + target.getNameString() + " &ewas renamed to &6" + name));
                target.setName(name);
            }
            case "skin" -> {
                if (args.length == 1) return false;
                NPC target = null;
                Integer selectedIndex = sender instanceof Player player ? DataManager.getPlayerData(player).getSelectedNPC() : DataManager.getGlobal().getConsoleSelectedNPC();
                String name = args[1];

                if (selectedIndex != null) {
                    target = NPCManager.getNPC(selectedIndex);
                }
                if (selectedIndex == null || target == null) {
                    sender.sendMessage(TextUtil.c("&cYou must select an NPC first"));
                    break;
                }

                target.sendRemovePacket();
                Posc.MAIN_WORLD.getEntity(target.getInteractionId()).remove();
                NPCManager.createNPC(target.getNameString(), target.getLocation(), name, target.getIndex());
                sender.sendMessage(TextUtil.c("&eSkin of &6" + target.getNameString() + " &ewas changed to &6" + name));
            }
            case "help" -> sender.sendMessage(TextUtil.c("""
                    
                    &6&l/NPC Subcommands
                    
                    &6/npc create <name> &e- &fCreates an NPC in your location, with your head rotation and the specified name
                    &6/npc sel [name | id] &e- &fIf the second argument is left blank, selects the NPC you are pointing at or the closest NPC, otherwise selects the NPC with the specified name or ID
                    &6/npc list &e- &fSends a list of all NPCs, their IDs, names, and locations
                    &6/npc delete [name | id] &e- &fIf the second argument is left blank, deletes the selected NPC, otherwise deletes the NPC with the specified name or ID
                    &6/npc rename <name> &e- &fRenames the selected NPC with the specified name (must not be longer than 16 characters)
                    &6/npc skin <name> &e- &fChanges the skin of the selected NPC to the skin of the player with the specified name
                    
                    """));
        }

        return true;
    }

    private NPC handleNPCFromName(String name, CommandSender sender, boolean delete) {
        name = name.replace("\"", "");

        NPC[] npcs = NPCManager.getNPC(name);
        if (npcs.length == 0) {
            sender.sendMessage(TextUtil.c("&cNo NPC with that name or ID found.\n&cDo /npc list for a list of NPCs."));
            return null;
        }
        if (npcs.length == 1) {
            return npcs[0];
        }
        sender.sendMessage(TextUtil.c("\n&e&lClick to " + (delete ? "delete" : "select") + " NPC\n"));
        for (NPC npc : npcs) {
            sender.sendMessage(TextUtil.c("&6" + npc.getIndex() + " &e- &6" + npc.getNameString() + " &e| &6" + TextUtil.sexyLocation(npc.getLocation()))
                    .hoverEvent(HoverEvent.showText(TextUtil.c("&e" + (delete ? "Delete" : "Select"))))
                    .clickEvent(ClickEvent.runCommand("/npc delete " + npc.getIndex())));
        }
        sender.sendMessage(TextUtil.empty());
        return null;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return switch (args.length) {
            case 1 -> CommandUtil.partialMatches(args[0], List.of("create", "sel", "list", "delete", "rename", "skin", "help"));
            case 2 -> switch (args[0]) {
                case "sel", "delete" -> CommandUtil.partialMatches(args[1], NPCManager.getNPCMap().values().stream()
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
