package me.harfang.helpcommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.ChatColor;


public class DiscordCommand implements CommandExecutor {

    public DiscordCommand(Main main) {
        main.getCommand("discord").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        if (args.length == 0) {
            TextComponent message = new TextComponent("Click here to join our discord server !");
            message.setColor(ChatColor.GOLD);
            message.setBold(true);
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/UYnUKyYp5Z"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click here to join the discord server !").color(ChatColor.GRAY).create()));
            player.spigot().sendMessage(message);
            return true;
        }

        return false;
    }
}
