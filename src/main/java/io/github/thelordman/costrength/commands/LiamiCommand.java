package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.discord.Discord;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class LiamiCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 5)) return true;
        Bukkit.broadcastMessage(Methods.cStr("&8[&71&8] &cOwner &8| &cLiami_&7: &f" + String.join(" ", args)));
        Discord.minecraftChatChannel.sendMessage("**[1] Owner | Liami_:**" + String.join(" ", args)).queue();
        return true;
    }
}