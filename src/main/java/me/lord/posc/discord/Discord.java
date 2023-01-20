package me.lord.posc.discord;

import me.lord.posc.Posc;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Discord {
    public static JDA jda;

    private static final String TOKEN = Posc.get().getConfig().getString("bot-token");

    public static TextChannel MINECRAFT_CHAT;
    public static TextChannel MINECRAFT_LOGS;
    public static TextChannel STAFF_CHAT;
    public static TextChannel ADMIN_CHAT;

    public static void enable() {
        jda = JDABuilder.createLight(TOKEN, GatewayIntent.GUILD_MESSAGES)
                .setActivity(Activity.competing("posc.minehut.gg"))
                .build();

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MINECRAFT_CHAT = jda.getTextChannelById(Posc.get().getConfig().getString("minecraft-chat-id"));
        MINECRAFT_LOGS = jda.getTextChannelById(Posc.get().getConfig().getString("minecraft-logs-id"));
        STAFF_CHAT = jda.getTextChannelById(Posc.get().getConfig().getString("staff-chat-id"));
        ADMIN_CHAT = jda.getTextChannelById(Posc.get().getConfig().getString("admin-chat-id"));

        MINECRAFT_CHAT.sendMessage(":green_circle: **Server Online**").queue();
    }

    public static void disable() {
        MINECRAFT_CHAT.sendMessage(":red_circle: **Server Offline**").queue();
        jda.shutdown();
    }
}
