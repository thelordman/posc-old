package me.lord.posc.commands;

import me.lord.posc.npc.NPCManager;
import me.lord.posc.utilities.Cmd;
import me.lord.posc.utilities.TextUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return null;
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
