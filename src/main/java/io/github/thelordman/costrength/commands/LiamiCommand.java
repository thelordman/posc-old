package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.CoStrength;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LiamiCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 5)) return true;
        Bukkit.broadcastMessage(Methods.cStr("&9Helper &8| &9Liami_&7:&f " + Methods.arrayToString(args)));
        CoStrength.minecraftChatChannel.sendMessage("**Helper | Liami_:** " + Methods.arrayToString(args)).queue();
        return true;
    }
}