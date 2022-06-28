package io.github.thelordman.costrength.commands;

import io.github.thelordman.costrength.utilities.Methods;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!Methods.checkCommandPermission(sender, (byte) 3)) return true;
        if (sender instanceof Player player) {
            for(int i = 0; i < 101; i++) {
                Bukkit.broadcast(Component.text(" "));
            }
            Bukkit.broadcast(Component.text(ChatColor.translateAlternateColorCodes('&', "&c[CHAT] &b" + player.getName() + " &7has cleared the chat!")));
            return true;
        }
        return false;
    }
}
