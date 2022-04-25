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

        if (!(event.getChannel() == Discord.minecraftChatChannel)) return;
        if (Objects.requireNonNull(event.getMember()).getId().equals("950449068444885062") && event.getMessage().getContentRaw().contains(":") | !event.getMessage().getEmbeds().isEmpty()) return;
        Role highestRole = event.getMember().getRoles().get(0).getId().equals("950088828067135548") ? event.getMember().getRoles().get(1) : event.getMember().getRoles().get(0);

        message = "&3Discord &8| &7(" + highestRole.getName() + ") " + Utilities.memberChatColor(event.getMember(), "primary") + event.getMember().getEffectiveName() + Utilities.memberChatColor(event.getMember(), "secondary") + ": " + Utilities.memberChatColor(event.getMember(), "primary") + event.getMessage().getContentDisplay();

        Bukkit.broadcastMessage(Methods.cStr(message));
    }
}
