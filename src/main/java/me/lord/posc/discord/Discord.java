package me.lord.posc.discord;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import me.lord.posc.Posc;

public class Discord {
    public static GatewayDiscordClient client;

    private static final String TOKEN = Posc.get().getConfig().getString("bot-token");

    public static TextChannel MINECRAFT_CHAT;
    public static TextChannel MINECRAFT_LOGS;
    public static TextChannel STAFF_CHAT;
    public static TextChannel ADMIN_CHAT;

    public static void enable() {
        client = DiscordClient.create(TOKEN).login().block();

        MINECRAFT_CHAT = (TextChannel) client.getChannelById(Snowflake.of(Posc.get().getConfig().getString("minecraft-chat-id"))).block();
        MINECRAFT_LOGS = (TextChannel) client.getChannelById(Snowflake.of(Posc.get().getConfig().getString("minecraft-logs-id"))).block();
        STAFF_CHAT = (TextChannel) client.getChannelById(Snowflake.of(Posc.get().getConfig().getString("staff-chat-id"))).block();
        ADMIN_CHAT = (TextChannel) client.getChannelById(Snowflake.of(Posc.get().getConfig().getString("admin-chat-id"))).block();

        client.updatePresence(ClientPresence.online(ClientActivity.competing("posc.minehut.gg"))).block();

        MINECRAFT_CHAT.createMessage(":green_circle: **Server Online**").block();
    }

    public static void disable() {
        MINECRAFT_CHAT.createMessage(":red_circle: **Server Offline**").block();
    }
}
