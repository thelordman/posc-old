package me.lord.posc.discord.listeners;

import me.lord.posc.discord.Discord;
import me.lord.posc.utilities.DiscordUtil;
import me.lord.posc.utilities.TextUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class MessageReceivedListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getGuild() != Discord.GUILD) return;

        if (!event.isWebhookMessage()) {
            Member member = event.getMember();
            Message message = event.getMessage();

            if (!(member.getUser() == Discord.jda.getSelfUser() && (message.getContentDisplay().contains(":") || !message.getEmbeds().isEmpty()))) {
                if (event.getChannel() == Discord.MINECRAFT_CHAT || event.getChannel() == Discord.STAFF_CHAT || event.getChannel() == Discord.ADMIN_CHAT) {
                    String role = DiscordUtil.getHighestRole(member) == null ? ""
                            : "(" + DiscordUtil.getHighestRole(member).getName() + ")";
                    if (role.equals("(*)")) {
                        role = DiscordUtil.getRole(member, 1) == null ? ""
                                : "(" + DiscordUtil.getRole(member, 1).getName() + ")";
                    }
                    if (event.getAuthor().isBot()) {
                        role = "(" + Discord.jda.getRoleById("921439677574160497").getName() + ")";
                    }

                    String channel = "";
                    if (event.getChannel() == Discord.STAFF_CHAT) channel = " Staff";
                    else if (event.getChannel() == Discord.ADMIN_CHAT) channel = " Admin";

                    Component finalMessage =
                            TextUtil.c("&7[&3Discord" + channel + "&7] " + role + " "
                            + (DiscordUtil.isPrivileged(member) ? "&f" : "&7") + member.getEffectiveName()
                            + (DiscordUtil.isPrivileged(member) ? "&7" : "&8") + ": "
                            + (DiscordUtil.isPrivileged(member) ? "&f" : "&7") + message.getContentDisplay());

                    if (event.getChannel() == Discord.STAFF_CHAT) Bukkit.broadcast(finalMessage, "posc.command.staffchat");
                    else if (event.getChannel() == Discord.ADMIN_CHAT) Bukkit.broadcast(finalMessage, "posc.command.adminchat");
                    else if (event.getChannel() == Discord.MINECRAFT_CHAT) Bukkit.broadcast(finalMessage);
                }
            }
        }
    }
}
