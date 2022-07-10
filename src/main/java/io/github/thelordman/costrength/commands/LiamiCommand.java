package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.discord.Discord;
import io.github.thelordman.costrength.utilities.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class LiamiCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 5)) return true;
        Bukkit.broadcastMessage(Methods.cStr(Methods.levelPrefix(UUID.fromString("ab5265d6-b739-4598-8945-da1c2779e021")) + " &cOwner &8| &cLiami&7: &f" + String.join(" ", args)));
        Discord.minecraftChatChannel.sendMessage("**" + Methods.replaceColorCodes(Methods.levelPrefix(UUID.fromString("ab5265d6-b739-4598-8945-da1c2779e021")), '&') + "Owner | Liami_:** " + String.join(" ", args)).queue();
        return true;
    }
}