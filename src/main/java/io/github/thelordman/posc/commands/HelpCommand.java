package io.github.thelordman.posc.commands;

import io.github.thelordman.posc.utilities.CommandName;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import net.md_5.bungee.api.ChatColor;

@CommandName("help")
public class HelpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            TextComponent message = new TextComponent("Click here to see the plugin's wiki for help.\nMine ores and kill players for money. Right click to upgrade tools and weapons.");
            message.setColor(ChatColor.GOLD);
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/thelordman/Posc/wiki"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click here to be sent to the plugin's wiki").color(ChatColor.GRAY).create()));
            player.spigot().sendMessage(message);
            return true;
        }
        return false;
    }
}