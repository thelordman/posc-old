package io.github.thelordman.costrength.discord.jdalisteners;

import io.github.thelordman.costrength.discord.Discord;
import io.github.thelordman.costrength.utilities.Methods;
import io.github.thelordman.costrength.discord.Utilities;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MessageReceivedListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String message;
        if (event.getChannel() != Discord.minecraftChatChannel && event.getChannel() != Discord.staffChatChannel) return;
        if (Objects.requireNonNull(event.getMember()).getId().equals("950449068444885062") && event.getMessage().getContentRaw().contains(":") | !event.getMessage().getEmbeds().isEmpty()) return;
        Role highestRole = null;
        if (!event.getMember().getRoles().isEmpty()) highestRole = event.getMember().getRoles().get(0).getId().equals("921439677590949936") ? event.getMember().getRoles().get(1) : event.getMember().getRoles().get(0);
        if (highestRole == Discord.jda.getRoleById("949767851139551273")) highestRole = event.getMember().getRoles().get(2);
        if (event.getAuthor().isBot()) highestRole = Discord.jda.getRoleById("921439677574160497");

        String role = highestRole == null ? "" : "&7(" + highestRole.getName() + ") ";
        if (event.getChannel() == Discord.minecraftChatChannel) {
            message = "&3Discord &8| " + role + Utilities.memberChatColor(event.getMember(), "primary")
                    + event.getMember().getEffectiveName() + Utilities.memberChatColor(event.getMember(), "secondary") + ": "
                    + Utilities.memberChatColor(event.getMember(), "primary") + event.getMessage().getContentDisplay();
        } else {
            message = "&3Discord Staff Chat &8| " + role + "&f" + event.getMember().getEffectiveName() + "&7: &f"
                    + event.getMessage().getContentDisplay();
        }

        Bukkit.broadcastMessage(Methods.cStr(message));
    }
}